package by.zemich.kufar.domain.service.textpostprocessors;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(9)
public class ConditionPostTextProcessor implements PostTextProcessor {

    @Override
    public String process(Advertisement advertisement) {
        return "▫️ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Состояние")) + advertisement.getCondition();
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return true;
    }
}
