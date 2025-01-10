package by.zemich.kufar.service.conditionanalizers;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.ProductConditionClassifier;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ScreenConditionClassifier implements ProductConditionClassifier {

    private final Pattern DEFECT_PATTERN = Pattern.compile(
            "(?i)(?x)" + // Игнорировать регистр и разрешить комментарии
                    "(" +
                    "присутству[юе]т\\s+(трещ[еи]нки)|" +
                    "разбита задняя пан[еэ]ль|" +
                    "трещин[аы]\\s+на\\s+([еиэ]кране|дисплее|дисплейном\\sмодуле)|" +
                    "(матрица|экран|дисплей)\\s+(теч[её]т|пот[ёе]к(ла)?)|" +
                    "([эие]кран|дисплейный\\s*модуль|дисплей)\\s+под\\s+замену|" +
                    "(скол\\s+на|трещин[аы]|бит[оеы]\\s+пиксел[ьией])" +
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
        if (advertisement == null) return false;
        if (advertisement.getDetails() == null) return false;
        return advertisement.getCategory().equalsIgnoreCase("17010")
                && !advertisement.getDetails().isEmpty()
                && !advertisement.getDetails().isBlank();
    }
}
