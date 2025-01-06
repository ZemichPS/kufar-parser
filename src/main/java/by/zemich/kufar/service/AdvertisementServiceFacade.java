package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dto.AdDetailsDTO;
import by.zemich.kufar.service.api.ConditionAnalyzer;
import by.zemich.kufar.service.clients.KufarClient;
import by.zemich.kufar.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdvertisementServiceFacade {
    private final AdvertisementService advertisementService;
    private final ConditionAnalyzer conditionAnalyzer;
    private final KufarClient kufarClient;
    private final ModelService modelService;

    public void updateAdvertisementCauseNewConditionRules() {
        final List<Advertisement> advertisementList = advertisementService.getAll().stream().parallel()
                .peek(advertisement -> {
                    String details = advertisement.getDetails();
                    boolean result = conditionAnalyzer.isFullyFunctional(details);
                    advertisement.setFullyFunctional(result);
                }).toList();
        log.info("выполнили проверку на состояние.");

        advertisementList.stream().parallel()
                .forEach(advertisementService::save);
    }

    public void parseSmartphonesAdsToDB() {
        modelService.getAll().stream()
                .map(model -> {
                    Long manufactureId = model.getManufacturer().getId();
                    String modelKufarId = model.getKufarId();
                    Map<String, String> paramMap = new HashMap<>();
                    paramMap.put("cat", "17010");
                    paramMap.put("lang", "ru");
                    paramMap.put("pb", manufactureId.toString());
                    paramMap.put("phm", modelKufarId);
                    paramMap.put("prn", "17000");
                    paramMap.put("size", "100");
                    paramMap.put("sort", "lst.d");
                    return paramMap;
                })
                .map(kufarClient::getAdsByParameters)
                .filter(Objects::nonNull)
                .flatMap(adsDTO -> adsDTO.getAds().stream().parallel())
                .filter(Objects::nonNull)
                .filter(adsDTO -> !advertisementService.existsByAdId(adsDTO.getAdId()))
                .map(Mapper::mapToEntity)
                .peek(advertisement -> {
                    AdDetailsDTO detailsDTO = kufarClient.getDetails(advertisement.getAdId());
                    String details = detailsDTO.getResult().getBody();
                    advertisement.setDetails(details);
                    advertisement.setFullyFunctional(conditionAnalyzer.isFullyFunctional(details));
                })
                .forEach(advertisementService::save);
    }

}
