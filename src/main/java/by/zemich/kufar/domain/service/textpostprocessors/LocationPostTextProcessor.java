package by.zemich.kufar.domain.service.textpostprocessors;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 5)
public class LocationPostTextProcessor implements PostTextProcessor {
    @Override
    public String process(Advertisement advertisement) {
        return "\uD83C\uDF0D %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Адрес")) + advertisement.getFullAddress();
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return true;
    }
}
