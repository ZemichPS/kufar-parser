package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class LocationPostTextProcessor implements PostTextProcessor {
    @Override
    public String process(Advertisement advertisement) {
        return "\uD83C\uDF0D %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Адрес")) + advertisement.getFullAddress();
    }
}
