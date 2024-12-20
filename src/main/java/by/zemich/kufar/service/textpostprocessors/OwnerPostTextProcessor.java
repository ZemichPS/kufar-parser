package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import org.springframework.stereotype.Component;

@Component
public class OwnerPostTextProcessor implements PostTextProcessor {
    @Override
    public String getLine(Advertisement advertisement) {
        return advertisement.isCompanyAd() ? "Объявление организации: да": "Объявление организации: нет";
    }
}
