package by.zemich.kufar.domain.service.textpostprocessors;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
@Component
@Order(9)
public class PublishTimePostTextProcessor implements PostTextProcessor {
    @Override
    public String process(Advertisement advertisement) {
        if(!isApplicable(advertisement)) return "";

        LocalDateTime utcPublishedAt = advertisement.getPublishedAt();
        ZonedDateTime utcTime = utcPublishedAt.atZone(ZoneId.of("UTC"));
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime localDatetimeWithZoneId = utcTime.withZoneSameInstant(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return PostTextProcessor.getBoldHtmlStyle("▫️ Время публикации: ") + formatter.format(localDatetimeWithZoneId);
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement.getPublishedAt() != null;
    }
}
