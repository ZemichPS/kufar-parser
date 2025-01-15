package by.zemich.kufar.domain.service.conditionanalizers.classifiers;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.service.conditionanalizers.classifiers.api.ProductConditionClassifier;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ScreenConditionClassifier implements ProductConditionClassifier {

    private final String SCREEN_PATTERN_GROUP = "(экран\\w*|матриц\\w*|диспл\\w*|стекл\\w*|тачскрин\\w*|молул\\w*|модуль|сенсор|тач)";

    private final Pattern DEFECT_PATTERN = Pattern.compile(
            "(?i)(" +
                    "(раз|по)?бит,\\s+но\\s+(работает|функционирует)|" +
                    "(треснут[оыа]?|(раз)?бит[аоы]?|дефект)\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "(треснут[оыа]?|(раз)?бит[аоы]?)\\s+(\\S+\\s+){2,}и\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    SCREEN_PATTERN_GROUP + "\\s+(оригинальное|оригинальный),?\\s*но (по)?бит(о|ый|а)|" +
                    "потрескан\\s+угол|" +
                    "упал\\sне\\s*удачно|" +
                    "не\\s*удачно\\s+упал|" +
                    "была\\s+(произведена\\s+)?замена\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "(вопросы?|нюансы?)\\s+(по|на)\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    SCREEN_PATTERN_GROUP + "\\s+((раз)?бит(о|ый|а)?|треснут\\w*)|" +
                    "дефект(ом)?\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "сколы?\\s+(в\\s+)?(углу|уголк[еу]|(с)?верху)?\\s*" + SCREEN_PATTERN_GROUP + "?|" +
                    "(требуется|необходим[оа]|нужн[ао]|над[ао])\\s+((за)?мен[ия]ть|замена|переклеить|заклеить)\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "(по|раз)?бит\\s+(спереди|перед)|" +
                    "(пот[её]к(ла)?|полетел(а)?|глючит)\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "(нюанс|проблема|дефект|траблы)\\s+с\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "(нюанс|проблема|дефект|траблы)\\s+это\\s+(выгорани[еия]|паутин[аы]|пятн[аоы])|" +
                    "нет\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "меня(лся|лась)\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "появи(лся|лась|лись)\\s+(полос[аы]|полоск[аи]|паутин(ка|ки|а|ы)|пятно|пятнышк[ао])|" +
                    "(отклеил(ся|лось)|отклеивается|отходит|отош[её]л)\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "(перестал\\s+работать|гореть|показывать)\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "не\\s+оригинальн(ый|ое)\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "остаточное\\s+изображение|" +
                    "(состоянии,?|состояние\\s+идеальное)\\s+(за\\s+исключением|кроме|не\\s+считая)\\s+((заднего\\s+)?" + SCREEN_PATTERN_GROUP + ")|" +
                    "(раз)?бит,?\\s*но\\s+(работает|рабочий)|" +
                    "рабочий,?\\s*но\\s+бит(ый)?|" +
                    "экран\\s+не\\s+показывает|" +
                    SCREEN_PATTERN_GROUP + "?\\s+(за)?менял(ась|ся|лись|лось)\\s*" + SCREEN_PATTERN_GROUP + "?|" +
                    SCREEN_PATTERN_GROUP + "\\s+не\\s+рабоч(ий|ая)|" +
                    "трещин[аы]\\s+\\S+\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "трещин[аы]\\s+\\S+\\s+\\S+\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "((небольш(ая|ые)|не\\s*значительн(ые|ая))\\s+)?(трещин[аы]|трещинк[иа])(\\s+на\\s+переднем\\s+стекле)?|" +
                    SCREEN_PATTERN_GROUP + "\\s+\\S+\\s+(прогорела?|выгорела?)|" +
                    SCREEN_PATTERN_GROUP + "\\s+(прогорела?|выгорела?)|" +
                    "прогорел(а)?\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "бит[аоы]?\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "трещин[аы]\\s+на\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "матрица\\s+потекл[аоы]|" +
                    "засвет\\s+на\\\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "полосы\\s+на\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    "пошли полосы|" +
                    "потекла\\s+матрица|" +
                    "(выгорел(а)?|выгорани[ея])\\s+(на\\s+)?" + SCREEN_PATTERN_GROUP + "|" +
                    "выгорание\\s+(экрана|дисплея)|" +
                    "не\\s+работает\\s+" + SCREEN_PATTERN_GROUP + "|" +
                    SCREEN_PATTERN_GROUP + "\\s+оригинальный,?\\s+но побит|" +
                    "(скол[ыа]?|трещин[аы]|полос[аы])\\s*(на\\s+)?" + SCREEN_PATTERN_GROUP + "|" +
                    "остал[ао]сь\\s+остаточн[оы]е\\s+изображени[ея]" +
                    ")"
    );

    @Override
    public boolean analyze(Advertisement advertisement) {
        if (!isApplicable(advertisement)) return false;
        String details = advertisement.getDetails().toLowerCase();
        return DEFECT_PATTERN.matcher(details).find();
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement != null
                && "17010".equalsIgnoreCase(advertisement.getCategory())
                && advertisement.getDetails() != null
                && !advertisement.getDetails().isBlank();
    }
}

