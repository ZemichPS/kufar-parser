package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dto.AdsDTO;
import by.zemich.kufar.service.api.PostPublisher;
import by.zemich.kufar.service.clients.KufarClient;
import by.zemich.kufar.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledService {
    private final AdvertisementService advertisementService;
    private final GeoService geoService;
    private final SubscriptionManager subscriptionManager;
    private final KufarClient kufarClient;
    private final List<PostPublisher> postPublishers;

    @Scheduled(initialDelay = 5_000, fixedDelay = 60_000)
    void parseAdsAndSaveIfNotExists() {
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
                .forEach(advertisement -> {
                   postPublishers.forEach(publisher -> {
                       try {
                           publisher.publish(advertisement);
                       } catch (Exception e) {
                           throw new RuntimeException(e);
                       }
                   });
                });
    }

    public void updateGeoData() {
        kufarClient.getGeoData().stream()
                .map(Mapper::mapToEntity)
                .forEach(geoService::save);
    }


}
