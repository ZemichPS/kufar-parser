package by.zemich.kufar.service.api;

import by.zemich.kufar.dao.entity.Advertisement;

public interface PostTextProcessor {
    String getLine(Advertisement advertisement);

    static String getItalicHtmlStyle(String source) {
        return "<i>" + source + "</i>";
    }

    static String getBoldHtmlStyle(String source) {
        return "<b>" + source + "</b>";
    }

    static String getHtmlLink(String targetLink, String textLink) {
        return """
                <a href="%s">%s</a>
                """.formatted(targetLink, textLink);
    }
}
