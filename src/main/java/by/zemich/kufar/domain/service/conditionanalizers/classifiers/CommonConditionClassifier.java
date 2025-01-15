package by.zemich.kufar.domain.service.conditionanalizers.classifiers;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.service.conditionanalizers.classifiers.api.ProductConditionClassifier;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CommonConditionClassifier implements ProductConditionClassifier {

    private final Pattern DEFECT_PATTERN = Pattern.compile(
            "(?i)(?x)" + // Игнорировать регистр и разрешить комментарии
                    "(" +
                    // Проблемы после падения или ремонта
                    "после\\s+(ремонта|замены)|" +
                    "(разбит[аоы]?|с?ломан[аоы]?|погнут[аоы]?|не\\s*работает|не\\s*функционирует|поврежд[её]н[аоы]?|спален[оыа]?|с?горел[оаи]?|погнут\\w*)\\s+(корпус|модем|проц|процессор|плата|(задняя\\s+)?крышк[аи]|динамик\\w*|микрофон\\w*|отпечаток|гнездо|разъ[её]м|сенсор\\w*)|" +
                    "поврежд[её]н[аоы]?|" +
                    "работа[ею]т\\s+вс[её],?\\s+(функции,?\\s+)?(\\S+\\s+)?кроме\\s+(сет[ьи]|кноп[оак]{1,3}|динамик\\w*|звук\\w*|микрофон\\w*|gps|зарядки)|" +
                    // Общие признаки неисправности
                    "не\\s+(включается|зар[яе]жа[ие]тся|ловит\\s+сеть|рабочае|работа\\w*)|" +
                    "(?:прода[мю]?)?\\s*(так\\s+как\\s+)?нету\\s+смысла\\s+(чинить|ремонтировать|восстановить|починить)|" +
                    "перестала?\\s+(работать|включаться|функционировать|ловить сеть|показывать|гореть)|" +
                    "нет\\s+(сети|[иэе]крана|диспл[еэ]я)|" +
                    "(под|для)\\s+восс?тановлени[ея]|" +
                    "работает\\s+сам\\s+по\\s*себе|" +
                    "может\\s+(сам\\s)?перезагру(жаться|зиться)|" +
                    "на\\s+(запчасти|зп)|" +
                    "в\\s+нерабочем\\s+сост[оа]янии|" +
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
        return advertisement != null
                && "17010".equalsIgnoreCase(advertisement.getCategory())
                && advertisement.getDetails() != null
                && !advertisement.getDetails().isBlank();
    }
}
