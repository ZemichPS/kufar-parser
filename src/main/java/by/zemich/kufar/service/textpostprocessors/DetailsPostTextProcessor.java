package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DetailsPostTextProcessor implements PostTextProcessor {

    @Override
    public String getLine(Advertisement advertisement) {
        return "Описание:" + advertisement.getDetails();
    }
}
