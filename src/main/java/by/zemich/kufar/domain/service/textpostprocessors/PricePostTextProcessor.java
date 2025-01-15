package by.zemich.kufar.domain.service.textpostprocessors;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.service.textpostprocessors.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Order(value = 11)
public class PricePostTextProcessor implements PostTextProcessor {
    @Override
    public String process(Advertisement advertisement) {
        StringBuilder sb = new StringBuilder();
        BigDecimal price = advertisement.getPriceInByn();

        String content = "\uD83C\uDFF7\uFE0F %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Цена")) + price;

        StringBuilder underscoresBuilder = new StringBuilder(content.length());
        for (int i = 0; i < content.length() + 4; i++) {
            underscoresBuilder.append('-');
        }

        String underline = underscoresBuilder.toString();
        return underline + "\n" + content + "\n" + underline;
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return true;
    }
}
