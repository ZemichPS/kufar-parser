package by.zemich.kufar.service.conditionanalizers;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.ProductConditionClassifier;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ScreenConditionClassifier implements ProductConditionClassifier {

    private final Pattern DEFECT_PATTERN = Pattern.compile(
            "(?i)(разбит,\\s+но\\s+работает|" +
                    "(треснут[оыа]?|(раз)?бит[аоы]?)\\s+(экран|стекло|диспле[йи]|матриц[аы])|" +
                    "(экран|стекло|дисплей|матрица)\\s+(оригинальное|оригинальный),?\\s*но (по)?бит(о|ый|а)|" +
                    "потрескан\\s+угол|" +
                    "упал\\sне\\s*удачно|" +
                    "не\\s*удачно\\s+упал|" +
                    "была\\s+(произведена\\s+)?замена\\s+(экрана|матрицы|дисплея|стекла)|" +
                    "дефект(ом)?\\s+(экрана?|матрицы|диспл(ея|й)|стекл[ао])|" +
                    "сколы?\\s+(в\\s+)?(углу|уголке)?\\s*(экрана|матрицы|дисплея|стекла)|" +
                    "побит\\s+спереди|" +
                    "пот[её]к(ла)?\\s+(матрица|экран|дисплей)|" +
                    "полетел(а)?\\s+(матрица|экран|дисплей)|" +
                    "нет\\s+(экрана|матрицы|дисплея)|" +
                    "меня(лся|лась)\\s+(экран|матрица|дисплей)|" +
                    "отклеил(ся|лось)\\s+(стекло|матрица|экран)|" +
                    "(раз)?бит,?\\s*но\\s+(работает|рабочий)|" +
                    "рабочий,?\\s*но\\s+бит(ый)?|" +
                    "экран\\s+не\\s+показывает|" +
                    "(экран|матрица|дисплей)\\s+(менял(ась|ся|лись))|" +
                    "(экран|матрица|дисплей)\\s+не\\s+рабоч(ий|ая)|" +
                    "трещин[аы]\\s+\\S+\\s+(экрана|дисплея|матрицы)|"+
                    "трещин[аы]\\s+\\S+\\s+\\S+\\s+(экрана|дисплея|матрицы)|"+
                    "трещин[аы]\\s+на\\s+переднем\\s+стекле|"+
                    "(экран|матрица|дисплей)\\s+\\S+\\s+(прогорела?|выгорела?)|" +
                    "(экран|матрица|дисплей)\\s+(прогорела?|выгорела?)|" +
                    "прогорел(а)?\\s+(экран|матрица)|" +
                    "бит[аоы]?\\s+(экран|дисплей|матрица)|" +
                    "трещин[аы]\\s+на\\s+(экране|дисплее|матрице)|" +
                    "матрица\\s+потекл[аоы]|" +
                    "засвет\\s+на\\\\s+(экране|дисплее|матрице)|" +
                    "отош[её]л\\s+экран|" +

                    "полосы\\s+на\\s+(экране|дисплее|матрице)|" +
                    "пошли полосы|" +
                    "потекла\\s+матрица|" +
                    "выгорел(а)?\\s+(дисплей|матрица|[эи]кран)|" +
                    "выгорание\\s+(экрана|дисплея)|" +
                    "экран\\s+треснут|" +
                    "дефект\\s+экрана|" +
                    "бит\\s+сенсор|" +
                    "не\\s+работает\\s+(дисплей|экран|матрица)|" +
                    "нужна\\s+замена\\s+(диспл[эе]я|экрана|матрицы)|" +
                    "нужно\\s+заменить\\s+(экран|дисплей|матрицу)|" +
                    "менял(ся|лось|лась)\\\\s+(дисплей|экран|матрица)|" +
                    "экран\\s+оригинальный,\\s+но побит|" +
                    "(скол[ыа]?|трещин[аы]|полос[аы])\\s*(на\\s+)?(экране|диспл[еэ]е|матрице)|" +
                    "остал[ао]сь\\s+остаточн[оы]е\\s+изображени[ея]" +
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

