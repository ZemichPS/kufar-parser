package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.ConditionAnalyzer;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstimationPostTextProcessor implements PostTextProcessor {

    @Override
    public String process(Advertisement advertisement) {
        String line = "▫️ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Оценка состояния"));
        return advertisement.isFullyFunctional() ? line + " ✅" : line + " ⚠️";
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return true;
    }
}
