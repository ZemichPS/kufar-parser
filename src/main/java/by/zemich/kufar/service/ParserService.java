package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dto.AdsDTO;
import by.zemich.kufar.service.clients.KufarClient;
import by.zemich.kufar.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParserService {
    private final AdvertisementService advertisementService;
    private final GeoService geoService;
    private final SubscriptionManager subscriptionManager;
    private final KufarClient kufarClient;

    public void parseAdsAndSaveIfNotExists() {
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
                .forEach(subscriptionManager::matchAndNotify);
    }

    public void updateGeoData() {
        kufarClient.getGeoData().stream()
                .map(Mapper::mapToEntity)
                .forEach(geoService::save);
    }


}
