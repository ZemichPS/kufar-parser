package by.zemich.kufar.domain.service.textpostprocessors;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.service.textpostprocessors.api.PostTextProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(12)
public class GetLinkTextProcessor implements PostTextProcessor {
    @Override
    public String process(Advertisement advertisement) {
        return PostTextProcessor.getHtmlLink(advertisement.getLink(), "ссылка на страницу товара kufar");
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return true;
    }


}
