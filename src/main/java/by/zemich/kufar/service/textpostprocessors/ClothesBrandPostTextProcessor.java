package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ClothesBrandPostTextProcessor implements PostTextProcessor {


    @Override
    public String process(Advertisement advertisement) {
        String brand = advertisement.getParameterValueByParameterName("women_clothes_brand").get();
        return "▫\uFE0F" + PostTextProcessor.getBoldHtmlStyle(" Бренд: ") + PostTextProcessor.getTag(brand);
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement.getParameterValueByParameterName("women_clothes_brand").isPresent();
    }
}
