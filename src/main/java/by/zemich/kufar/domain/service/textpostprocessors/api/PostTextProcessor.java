package by.zemich.kufar.domain.service.textpostprocessors.api;

import by.zemich.kufar.domain.model.Advertisement;

public interface PostTextProcessor {
    String process(Advertisement advertisement);

    boolean isApplicable(Advertisement advertisement);

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

    static String getTag(String source) {
        return "#" + source.trim()
                .replaceAll("&", "and")
                .replaceAll(",","")
                .replaceAll("\\s+","_");
    }

}
