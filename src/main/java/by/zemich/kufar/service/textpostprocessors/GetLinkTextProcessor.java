package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Integer.MAX_VALUE)
public class GetLinkTextProcessor implements PostTextProcessor {
    @Override
    public String process(Advertisement advertisement) {
        return PostTextProcessor.getHtmlLink(advertisement.getLink(), "ссылка");
    }


}
