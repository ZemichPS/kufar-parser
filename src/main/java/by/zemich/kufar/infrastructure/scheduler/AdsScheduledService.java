package by.zemich.kufar.infrastructure.scheduler;

import by.zemich.kufar.application.service.*;
import by.zemich.kufar.application.service.advertisementhandlers.api.AdvertisementHandler;
import by.zemich.kufar.application.service.api.AdvertisementPublisher;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.model.Category;
import by.zemich.kufar.domain.model.Manufacturer;
import by.zemich.kufar.domain.service.conditionanalizers.ConditionAnalyzer;
import by.zemich.kufar.infrastructure.clients.KufarClient;
import by.zemich.kufar.infrastructure.clients.NIOKufarClient;
import by.zemich.kufar.infrastructure.clients.dto.CategoriesDto;
import by.zemich.kufar.infrastructure.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class AdsScheduledService {
    private final AdvertisementService advertisementService;
    private final KufarClient kufarClient;
    private final List<AdvertisementPublisher> advertisementPublishers;
    private final ConditionAnalyzer conditionAnalyzer;
    private final List<AdvertisementHandler> advertisementHandlers;
    private final NIOKufarClient nioKufarClient;


    @Scheduled(initialDelay = 20_000, fixedDelay = 10_000)
    public void getNewAdsAndSaveIfNotExists() {

        List<String> categories = List.of(
                "17010",
                "8110",
                "8100",
                "8080",
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
                                            Mono.fromRunnable(() -> publisher.publish(ad))
                                                    .delayElement(generateRandomDelay())
                                                    .retryWhen(
                                                            Retry.backoff(20, Duration.ofSeconds(15))
                                                                    .maxBackoff(Duration.ofSeconds(20))
                                                                    .doBeforeRetry(retrySignal ->
                                                                            log.warn("Retry to send advertisement... attempt {}, cause: {}", retrySignal.totalRetries(), retrySignal.failure().getMessage()))
                                                    )
                                                    .doOnError(e -> log.error("Failed to publish advertisement with id: {}", ad.getId(), e))
                                                    .subscribe();
                                        });
                                    }
                            )
                            .doOnError(error -> log.error("Error: {}", error.getMessage()))
                            .subscribe();
                });


    }

    private Advertisement handleAdvertisement(Advertisement advertisement) {
        advertisementHandlers.stream()
                .filter(advertisementHandler -> advertisementHandler.canHandle(advertisement))
                .forEach(advertisementHandler -> advertisementHandler.handle(advertisement));
        return advertisement;
    }


    private Duration generateRandomDelay() {
        long randomMillis = ThreadLocalRandom.current().nextLong(1_200, 5_000);
        return Duration.ofMillis(randomMillis);
    }

}
