package by.zemich.kufar.service;

import by.zemich.kufar.service.api.ConditionAnalyzer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceFacade {
    private final AdvertisementService advertisementService;
    private final ConditionAnalyzer conditionAnalyzer;

    public void updateAdvertisementCauseNewConditionRules(){
        advertisementService.getAll().stream()
                .peek(advertisement -> {
                    String details = advertisement.getDetails();
                    boolean result = conditionAnalyzer.isFullyFunctional(details);
                    advertisement.setFullyFunctional(result);
                })
                .forEach(advertisementService::save);
    }

}
