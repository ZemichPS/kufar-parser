package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.entity.Manufacturer;
import by.zemich.kufar.dto.AdDetailsDTO;
import by.zemich.kufar.dto.AdsDTO;
import by.zemich.kufar.service.api.AdvertisementPublisher;
import by.zemich.kufar.service.clients.KufarClient;
import by.zemich.kufar.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

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
    private final PostManager postManager;

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
                    return advertisementService.save(advertisement);
                })
                //.parallel()
                .forEach(advertisement -> {
                    advertisementPublishers.forEach(publisher -> {
                        try {
                            publisher.publish(advertisement);
                        }catch (TelegramApiRequestException telegramApiRequestException){
                            log.error("Failed to notify post cause of {}. Try to sleep and push again", telegramApiRequestException.toString());
                            try {
                                sleep(5000);
                                try {
                                    publisher.publish(advertisement);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            } catch (InterruptedException e) {
                                log.error("Failed to notify post {}", advertisement);
                            }
                        } catch (Exception e) {
                            //throw new RuntimeException(e);
                            log.error("Failed to notify post in {}, Cause: {}. ", publisher.getClass().getName(), e.toString());
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
