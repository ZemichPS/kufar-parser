package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Order(value = 2)
@Component
public class DetailsPostTextProcessor implements PostTextProcessor {

    @Override
    public String process(Advertisement advertisement) {
        String details = advertisement.getDetails();
        String removed = removeExtraCharacters(details);
        String prepared = reduce(removed);
        return "⋮ %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Описание")) + PostTextProcessor.getItalicHtmlStyle(advertisement.getSubject() + ". " + prepared);
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return true;
    }


    private String reduce(String source) {
        if (source.length() >= 400) return source.substring(0, 400) + "...(смотри на сайте)";
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
