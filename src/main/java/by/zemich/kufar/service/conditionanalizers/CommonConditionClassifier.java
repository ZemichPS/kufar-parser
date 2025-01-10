package by.zemich.kufar.service.conditionanalizers;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.ProductConditionClassifier;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CommonConditionClassifier implements ProductConditionClassifier {

    private final Pattern DEFECT_PATTERN = Pattern.compile(
            "(?i)(?x)" + // Игнорировать регистр и разрешить комментарии
                    "(" +
                    // Проблемы после падения или ремонта
                    "после\\s+(падения.*полоса|ремонта)|" +
                    "поврежд[её]н[аоы]?|" +
                    // Общие признаки неисправности
                    "не\\s+(включается|ловит\\s+сеть|рабочае)|" +
                    "(?:прода[юю]?)?\\s*(так\\s+как\\s+)?нету\\s+смысла\\s+(чинить|ремонтировать|восстановить|починить)|" +
                    "перестал(а)\\s+(работать)|" +
                    "нет\\s+(сети|[иэе]крана|диспл[еэ]я)|" +
                    "под\\s+восстановлени[ея]|" +
                    "может\\s+(сам\\s)?перезагру(жаться|зиться)|" +
                    "на\\s+запчасти|" +
                    "на\\s+зп|" +
                    "в\\s+нерабочем\\s+сост[оа]янии|" +
                    "не\\s+включается|" +
                    "пару\\s+(трещин|трещинок)|" +
                    "выгоревш[ие]й\\s+([эеи]кран|дисплей|дисплейный\\sмодуль)|" +
                    "сильн[оа]\\s+разбит|" +
                    "не\\s+(видит|определя[еи]т)\\s+(sim|сим)\\s+(карт[уы])?|" +
                    "вс[её]\\s+работает\\s+кроме\\s+" +
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
