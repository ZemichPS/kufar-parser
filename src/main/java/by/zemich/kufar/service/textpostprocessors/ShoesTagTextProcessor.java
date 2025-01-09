package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(12)
public class ShoesTagTextProcessor implements PostTextProcessor {
    @Override
    public String process(Advertisement advertisement) {
        String value = advertisement.getParameterValueByParameterName("women_shoes_type").get();
        if (value.isEmpty() || value.isBlank()) return "";
        return PostTextProcessor.getTag(value);
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement.getParameterValueByParameterName("women_shoes_type").isPresent();
    }
}
