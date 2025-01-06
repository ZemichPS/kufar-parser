package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.ConditionAnalyzer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdvertisementServiceFacade {
    private final AdvertisementService advertisementService;
    private final ConditionAnalyzer conditionAnalyzer;

    public void updateAdvertisementCauseNewConditionRules() {
        final List<Advertisement> advertisementList = advertisementService.getAll().stream()
                .peek(advertisement -> {
                    String details = advertisement.getDetails();
                    boolean result = conditionAnalyzer.isFullyFunctional(details);
                    advertisement.setFullyFunctional(result);
                }).toList();
        log.info("выполнили проверку на состояние.");
        advertisementList.forEach(advertisementService::save);
        log.info("выполнили сохранение всех объектов в базу");
    }

}
