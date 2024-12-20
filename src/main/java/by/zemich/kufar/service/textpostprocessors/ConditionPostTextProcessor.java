package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.ConditionAnalyzer;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConditionPostTextProcessor implements PostTextProcessor {

    private final ConditionAnalyzer conditionAnalyzer;

    @Override
    public String getLine(Advertisement advertisement) {
        if (conditionAnalyzer.analyze(advertisement.getDetails())) return "Состояние: ✅";
        return "Состояние: ⚠\uFE0F";
    }
}
