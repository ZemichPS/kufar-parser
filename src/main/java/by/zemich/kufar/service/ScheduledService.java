package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.entity.Manufacturer;
import by.zemich.kufar.dto.AdDetailsDTO;
import by.zemich.kufar.dto.AdsDTO;
import by.zemich.kufar.service.api.PostPublisher;
import by.zemich.kufar.service.clients.KufarClient;
import by.zemich.kufar.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ScheduledService {
    private final AdvertisementService advertisementService;
    private final GeoService geoService;
    private final KufarClient kufarClient;
    private final List<PostPublisher> postPublishers;
    private final ManufactureService manufactureService;
    private final ModelService modelService;
    private final ConditionAnalyzer conditionAnalyzer;

    @Scheduled(initialDelay = 5_000, fixedDelay = 30_000)
    public void getNewAdsAndSaveIfNotExists() {
        AdsDTO response = kufarClient.getNewAds();

        response.getAds().stream()
                .filter(dto -> !advertisementService.existsByAdId(dto.getAdId()))
                .map(adDTO -> {
                    Advertisement advertisement = Mapper.mapToEntity(adDTO);
                    adDTO.getAdParameters().stream()
                            .map(Mapper::mapToEntity)
                            .forEach(advertisement::addParameter);
                    return advertisement;
                })
                .map(advertisementService::save)
                .map(advertisement -> {
                    AdDetailsDTO detailsDTO = kufarClient.getDetails(advertisement.getAdId());
                    String details = detailsDTO.getResult().getBody();
                    advertisement.setDetails(details);
                    advertisement.setFullyFunctional(conditionAnalyzer.isFullyFunctional(details));
                    log.info("устройство полностью исправно: {}", advertisement.isFullyFunctional());
                    return advertisementService.save(advertisement);
                })
                //.parallel()
                .forEach(advertisement -> {
                    postPublishers.forEach(publisher -> {
                        try {
                            publisher.publish(advertisement);
                        } catch (Exception e) {
                            //throw new RuntimeException(e);
                            log.error("Failed to publish post in {}, Cause: {}. ", publisher.getClass().getName(), e.toString());
                        }
                    });
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


}
