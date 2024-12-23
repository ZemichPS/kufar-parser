package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class GetLinkTextProcessor implements PostTextProcessor {
    @Override
    public String getLine(Advertisement advertisement) {
        return getHtmlLink(advertisement.getLink());
    }

    private String getHtmlLink(String sourceLink) {
        return """
                <a href="%s">ссылка</a>
                """.formatted(sourceLink);
    }
}
