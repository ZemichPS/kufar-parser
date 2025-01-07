package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 7)
public class OwnerPostTextProcessor implements PostTextProcessor {
    @Override
    public String process(Advertisement advertisement) {
        String text = PostTextProcessor.getBoldHtmlStyle("Объявление организации");
        return advertisement.isCompanyAd() ? "▫️ %s: да".formatted(text)  : "▫️  %s: нет".formatted(text);
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement.getCategory().equalsIgnoreCase("17010");
    }
}
