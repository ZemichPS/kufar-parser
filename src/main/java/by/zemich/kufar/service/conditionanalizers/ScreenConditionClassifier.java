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
                    "разбит[аоы] экран|экран не показывает|прогорел экран|бит[аоы] экран|трещин[аы] на экране|матрица потекл[аоы]|засвет на экране|отош[её]л экран|отклеил[ао]сь стекло|полосы на экране|пошли полосы|потекла матрица|выгорел дисплей|выгорание экрана|экран треснут|дефект экрана|бит сенсор|не работает дисплей|нужна замена дисплея|нужно заменить экран|менял[ао] дисплей|экран оригинальный, но побит|остались остаточные изображения|" +
                    "разбита задняя пан[еэ]ль|" +
                    "трещин[аы]\\s+на\\s+([еиэ]кране|дисплее|дисплейном\\sмодуле)|" +
                    "(матрица|экран|дисплей)\\s+(теч[её]т|пот[ёе]к(ла)?)|" +
                    "([эие]кран|дисплейный\\s*модуль|дисплей)\\s+под\\s+замену|" +
                    "(скол\\s+на|трещин[аы]|бит[оеы]\\s+пиксел[ьией])" +
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
        return advertisement != null
                && "17010".equalsIgnoreCase(advertisement.getCategory())
                && advertisement.getDetails() != null
                && !advertisement.getDetails().isBlank();
    }
}
