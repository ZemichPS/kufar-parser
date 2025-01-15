package by.zemich.kufar.domain.service.textpostprocessors;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(12)
public class ClothesTagTextProcessor implements PostTextProcessor {

    @Override
    public String process(Advertisement advertisement) {
        String value = advertisement.getParameterValueByParameterName("women_clothes_type").get();
        if (value.isEmpty() || value.isBlank()) return "";
        return PostTextProcessor.getTag(value);
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement.getParameterValueByParameterName("women_clothes_type").isPresent();
    }


}
