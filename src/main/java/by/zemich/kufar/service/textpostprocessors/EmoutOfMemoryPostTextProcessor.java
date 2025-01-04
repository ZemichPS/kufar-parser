package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmoutOfMemoryPostTextProcessor implements PostTextProcessor {

    @Override
    public String process(Advertisement advertisement) {
        String memoryAmount = advertisement.getParameterValueByParameterName("phablet_phones_memory")
                .orElse("");
        return memoryAmount.isEmpty() ? "" : "▫️ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Объём памяти")) + memoryAmount;
    }
}
