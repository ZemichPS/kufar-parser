package by.zemich.kufar.domain.service.textpostprocessors;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.service.textpostprocessors.api.PostTextProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(value = 2)
@Component
public class DetailsPostTextProcessor implements PostTextProcessor {

    @Override
    public String process(Advertisement advertisement) {
        String details = advertisement.getDetails();
        String removed = removeExtraCharacters(details);
        if (advertisement.getCategory().equalsIgnoreCase("17010")) {
            removed = (advertisement.getSubject() + ". " + removed);
        }
        String prepared = reduce(removed);
        return "⋮ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Описание")) + PostTextProcessor.getItalicHtmlStyle(prepared);
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return true;
    }


    private String reduce(String source) {
        if (source.length() >= 350) return source.substring(0, 350) + "...(смотри на сайте)";
        return source;
    }

    private String removeExtraCharacters(String source) {
        return source.replaceAll("-{2,}", "") // Убираем несколько дефисов подряд
                .replaceAll("={2,}", " ")  // Заменяем несколько знаков равно на пробел
                .replaceAll("\\*{2,}", " ") // Заменяем несколько звездочек на пробел
                .replaceAll("_{2,}", " ")   // Заменяем несколько нижних подчеркиваний на пробел
                .replaceAll(",(\\S)", ", $1") // Добавляем пробел после запятой, если его нет
                .replaceAll("\\.(?!\\s|$)", ". ") // Добавляем пробел после точки, если его нет
                .replaceAll("\\s{2,}", " ") // Заменяем несколько пробелов на один
                .trim();  // Убираем лишние пробелы в начале и в конце строки
    }

    private String removeEmptyStrings(String source) {
        return source.replaceAll("\\s+", "");
    }
}
