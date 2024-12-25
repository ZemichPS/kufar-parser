package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmoutOfRamPostTextProcessor implements PostTextProcessor {

    @Override
    public String getLine(Advertisement advertisement) {

    String ramAmount = advertisement.getParameterValueByParameterName("phablet_phones_ram")
                .orElse("");
        return ramAmount.isEmpty() ? "" : "▫️ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Объём ОЗУ")) + ramAmount;
    }
}
