package by.zemich.kufar.infrastructure.scheduler;

import by.zemich.kufar.application.service.*;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.model.Category;
import by.zemich.kufar.domain.model.Manufacturer;
import by.zemich.kufar.infrastructure.clients.dto.CategoriesDto;
import by.zemich.kufar.application.service.advertisementhandlers.api.AdvertisementHandler;
import by.zemich.kufar.application.service.api.AdvertisementPublisher;
import by.zemich.kufar.domain.service.conditionanalizers.ConditionAnalyzer;
import by.zemich.kufar.infrastructure.clients.KufarClient;
import by.zemich.kufar.infrastructure.clients.NIOKufarClient;
import by.zemich.kufar.infrastructure.utils.Mapper;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.lang.Thread.sleep;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ScheduledService {
    private final AdvertisementService advertisementService;
    private final GeoService geoService;
    private final KufarClient kufarClient;
    private final List<AdvertisementPublisher> advertisementPublishers;
    private final ManufactureService manufactureService;
    private final ModelService modelService;
    private final ConditionAnalyzer conditionAnalyzer;
    private final CategoryService categoryService;
    private final List<AdvertisementHandler> advertisementHandlers;
    private final NIOKufarClient nioKufarClient;


    @Scheduled(initialDelay = 1_000, fixedDelay = 10_000)
    public void getNewAdsAndSaveIfNotExists() {

        List<String> categories = List.of(
                "17010"
        );


        categories.stream()
                .parallel()
                .map(kufarClient::getNewAdsByCategoryIdAndByLastSort)
                .flatMap(adsDTO -> adsDTO.getAds().stream().parallel())
                .filter(Objects::nonNull)
                .filter(dto -> !advertisementService.existsByPublishedAt(dto.getListTime(), dto.getAdId(), dto.getCategory()))
                .map(adDTO -> {
                    Advertisement advertisement = Mapper.mapToEntity(adDTO);
                    adDTO.getAdParameters().stream()
                            .map(Mapper::mapToEntity)
                            .forEach(advertisement::addParameter);
                    return advertisement;
                })
                .map(this::handleAdvertisement)
                .forEach(advertisement -> {
                    nioKufarClient.getDetails(advertisement.getAdId())
                            .map(detailsDTO -> {
                                String details = detailsDTO.getResult().getBody();
                                advertisement.setDetails(details);
                                return advertisement;
                            })
                            .map(ad -> {
                                ad.setFullyFunctional(conditionAnalyzer.isFullyFunctional(advertisement));
                                advertisementService.save(ad);
                                return ad;
                            })
                            .doOnSuccess(ad ->
                                    {
                                        advertisementPublishers.forEach(publisher -> {
                                            Mono.fromCallable(() -> publisher.publish(ad))
                                                    .retryWhen(
                                                            Retry.backoff(50, Duration.ofSeconds(5))
                                                                    .maxBackoff(Duration.ofSeconds(25))
                                                                    .doBeforeRetry(retrySignal ->
                                                                            log.warn("Retry to send advertisement... attempt {}, cause: {}", retrySignal.totalRetries(), retrySignal.failure().getMessage()))
                                                    )
                                                    .doOnError(e -> log.error("Failed to publish advertisement with id: {}", ad.getId(), e))
                                                    .subscribe(); // Подписываемся, чтобы запустить выполнение
                                        });
                                    }
                            )
                            .doOnError(error -> log.error("Error: {}", error.getMessage()))
                            .subscribe();
                });


    }

    @Scheduled(initialDelay = 5_000, fixedDelay = 21_600_000)
    public void updateGeoData() {
        kufarClient.getGeoData().stream()
                .map(Mapper::mapToEntity)
                .forEach(geoService::save);
    }

    @Scheduled(initialDelay = 5_000, fixedDelay = 21_600_000)
    public void getAndUpdateManufacturesAndModelsList() {
        kufarClient.getFilledManufacture()
                .forEach(manufacturerDto -> {
                    manufactureService.getById(manufacturerDto.getId())
                            .ifPresentOrElse(
                                    manufacturer -> {
                                        manufacturerDto.getModels().stream()
                                                .filter(modelDto -> !modelService.existsByName(modelDto.getName()))
                                                .map(Mapper::mapToEntity)
                                                .forEach(model -> {
                                                    manufacturer.addModel(model);
                                                    manufactureService.save(manufacturer);
                                                });
                                    },
                                    () -> {
                                        Manufacturer manufacturer = Mapper.mapToEntity(manufacturerDto);
                                        manufacturerDto.getModels().stream()
                                                .filter(Objects::nonNull)
                                                .map(Mapper::mapToEntity)
                                                .forEach(manufacturer::addModel);
                                        manufactureService.save(manufacturer);
                                    });
                });
    }

    @Scheduled(initialDelay = 10_000, fixedDelay = 30_00_000)
    public void getAndUpdateCategories() {
        CategoriesDto categories = kufarClient.getCategories();
        categories.getCategories().stream()
                .map(categoryDto -> {
                    Category category = Mapper.mapToEntity(categoryDto);
                    categoryDto.getSubcategories().stream()
                            .map(Mapper::mapToEntity)
                            .forEach(category::addSubcategory);
                    return category;
                })
                .forEach(categoryService::save);

    }

    private Advertisement handleAdvertisement(Advertisement advertisement) {
        advertisementHandlers.stream()
                .filter(advertisementHandler -> advertisementHandler.canHandle(advertisement))
                .forEach(advertisementHandler -> advertisementHandler.handle(advertisement));
        return advertisement;
    }


}
