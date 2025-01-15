package by.zemich.kufar.domain.service.textpostprocessors;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.service.textpostprocessors.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstimationPostTextProcessor implements PostTextProcessor {

    @Override
    public String process(Advertisement advertisement) {
        String line = "▫️ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Оценка состояния"));
        return advertisement.isFullyFunctional() ? line + " ✅" : line + " ⚠️";
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement.getCategory().equalsIgnoreCase("17010");
    }
}
