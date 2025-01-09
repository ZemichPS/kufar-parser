package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ClothesSizePostTextProcessor implements PostTextProcessor {


    @Override
    public String process(Advertisement advertisement) {
        String size = advertisement.getParameterValueByParameterName("women_clothes_size").get();
        return "▫\uFE0F" + PostTextProcessor.getBoldHtmlStyle(" Размер: ") + size;
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement.getParameterValueByParameterName("women_clothes_size").isPresent();
    }
}
