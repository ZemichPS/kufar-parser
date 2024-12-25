package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.ConditionAnalyzer;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class ConditionPostTextProcessor implements PostTextProcessor {

    @Override
    public String getLine(Advertisement advertisement) {
        return "▫️ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Состояние")) + advertisement.getCondition();
    }
}
