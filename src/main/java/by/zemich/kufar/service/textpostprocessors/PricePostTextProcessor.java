package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PricePostTextProcessor implements PostTextProcessor {
    @Override
    public String process(Advertisement advertisement) {
        return "\uD83C\uDFF7\uFE0F %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Цена")) + advertisement.getPriceInByn();
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return true;
    }
}
