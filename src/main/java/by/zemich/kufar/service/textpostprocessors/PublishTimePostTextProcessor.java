package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//@Component
@Order(9)
public class PublishTimePostTextProcessor implements PostTextProcessor {
    @Override
    public String process(Advertisement advertisement) {
        if(!isApplicable(advertisement)) return "";

        LocalDateTime publishedAt = advertisement.getPublishedAt();
        return PostTextProcessor.getBoldHtmlStyle("▫️ Время публикации: ") + publishedAt.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement.getPublishedAt() != null;
    }
}
