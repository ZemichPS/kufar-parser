package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(9)
public class ConditionPostTextProcessor implements PostTextProcessor {

    @Override
    public String process(Advertisement advertisement) {
        return "▫️ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Состояние")) + advertisement.getCondition();
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return true;
    }
}
