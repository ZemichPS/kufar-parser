package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DetailsPostTextProcessor implements PostTextProcessor {

    @Override
    public String getLine(Advertisement advertisement) {
        String details = advertisement.getDetails();
        String removed = removeExtraCharacters(details);
        String prepared = reduce(removed);
        return "Описание: " + getHtmlStyle(prepared);
    }

    private String getHtmlStyle(String source) {
        return "<i>" + source + "</i>";
    }

    private String reduce(String source) {
        if (source.length() >= 400) return source.substring(0, 400) + "...(смотри на сайте)";
        return source;
    }

    private String removeExtraCharacters(String source) {
        return source.replaceAll("-{2,}", "")
                .replaceAll("={2,}", " ")
                .replaceAll("\\*{2,}", " ")
                .replaceAll("_{2,}", " ")
                .replaceAll(",(\\S)", ", $1")
                .replaceAll("\\.(?!\\s|$)", ". ")
                .replaceAll("\\s{2,}", " ");
    }

    private String removeEmptyStrings(String source) {
        return source.replaceAll("\\s+", "");
    }
}
