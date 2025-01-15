package by.zemich.kufar.domain.service.textpostprocessors;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.service.EmojiService;
import by.zemich.kufar.domain.service.textpostprocessors.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(value = 1)
public class HeaderPostTextProcessor implements PostTextProcessor {

    private final EmojiService emojiService;

    @Override
    public String process(Advertisement advertisement) {
        String header;
        if (advertisement.getBrand().isPresent()) {
            String brand = advertisement.getBrand().orElse("");
            String model = advertisement.getModel().orElse("(%s)".formatted(advertisement.getSubject()));
            header = brand + " " + model;
        } else {
            header = advertisement.getSubject();
        }
        return emojiService.getByCategory(advertisement.getCategory()) + " %s".formatted(getHtmlStyle(header));
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return (advertisement.getBrand().isPresent() && advertisement.getModel().isPresent()) || advertisement.getSubject() != null;
    }

    private String getHtmlStyle(String source) {
        return "<b>" + source + "</b>";
    }
}
