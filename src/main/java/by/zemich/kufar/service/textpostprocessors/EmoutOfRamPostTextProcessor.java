package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(value = 4)
public class EmoutOfRamPostTextProcessor implements PostTextProcessor {

    @Override
    public String process(Advertisement advertisement) {

    String ramAmount = advertisement.getParameterValueByParameterName("phablet_phones_ram")
                .orElse("");
        return ramAmount.isEmpty() ? "" : "▫️ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Объём ОЗУ")) + ramAmount;
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement.getParameterValueByParameterName("phablet_phones_ram").isPresent();
    }
}
