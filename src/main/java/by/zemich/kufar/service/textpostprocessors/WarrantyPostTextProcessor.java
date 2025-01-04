package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class WarrantyPostTextProcessor implements PostTextProcessor {

    private final Pattern WARRANTY_PRESENT_PATTERN = Pattern.compile(
            "\\b(гарантия есть|на гарантии|с гарантией|гарантия до \\d{1,2}\\.\\d{1,2}\\.\\d{4}|есть гарантия)\\b",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public String process(Advertisement advertisement) {
        String details = advertisement.getDetails();
        return "▫️ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Наличие гарантии")) + (warrantyChecker(details) ? "есть": "неизвестно");
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return true;
    }

    private boolean warrantyChecker(String details) {
        return WARRANTY_PRESENT_PATTERN.matcher(details).find();
    }
}
