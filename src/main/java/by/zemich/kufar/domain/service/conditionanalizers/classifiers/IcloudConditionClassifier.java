package by.zemich.kufar.domain.service.conditionanalizers.classifiers;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.service.conditionanalizers.classifiers.api.ProductConditionClassifier;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class IcloudConditionClassifier implements ProductConditionClassifier {

    private final Pattern DEFECT_PATTERN = Pattern.compile(
            "(?i)(?x)" + // Игнорировать регистр и разрешить комментарии
                    "(" +
                    "заблокирован\\s+(?:на\\s+)?iCloud|" +
                    "забыт[ао]?\\s+пароль(?:\\s+от)?\\s+iCloud|" +
                    "нет\\s+пароля(?:\\s+от)?\\s+iCloud" +
                    ")"
    );

    @Override
    public boolean analyze(Advertisement advertisement) {
        if (!isApplicable(advertisement)) return false;
        String details = advertisement.getDetails();
        return DEFECT_PATTERN.matcher(details.toLowerCase()).find();
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement != null
                && "17010".equalsIgnoreCase(advertisement.getCategory())
                && advertisement.getDetails() != null
                && !advertisement.getDetails().isBlank();
    }
}
