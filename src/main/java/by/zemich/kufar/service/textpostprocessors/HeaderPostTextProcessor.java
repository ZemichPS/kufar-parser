package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(value = 1)
public class HeaderPostTextProcessor implements PostTextProcessor {

    @Override
    public String getLine(Advertisement advertisement) {
        String brand = advertisement.getBrand();
        String model = advertisement.getModel();
        String header = brand + " " + model;
        return "\uD83D\uDCF1 %s".formatted(getHtmlStyle(header));
    }

    private String getHtmlStyle(String source) {
        return "<b>" + source + "</b>";
    }
}
