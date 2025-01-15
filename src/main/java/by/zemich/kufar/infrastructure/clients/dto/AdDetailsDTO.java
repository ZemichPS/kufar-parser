package by.zemich.kufar.infrastructure.clients.dto;

import by.zemich.kufar.infrastructure.config.VlDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;

@Data
public class AdDetailsDTO {
    private Result result;

    @Data
    public static class Result {
        private String accountId;
        private List<Parameter> accountParameters;
        private Long adId;
        private String adLink;
        private List<Parameter> adParameters;
        private String body;
        private String category;
        private boolean companyAd;
        private String currency;
        private List<Image> images;
        @JsonProperty("is_mine")
        private boolean isMine;
        private Long listId;
        private String listTime;
        private String messageId;
        private PaidServices paidServices;
        private boolean phoneHidden;
        private String priceByn;
        private String priceEur;
        private String priceUsd;
        private String remunerationType;
        private ShowParameters showParameters;
        private String subject;
        private String type;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Parameter {
        private String pl; // Заголовок параметра
        @JsonDeserialize(using = VlDeserializer.class)
        private String vl; // Значение для отображения
        private String p;  // Код параметра
        private Object v;  // Фактическое значение
        private String pu; // Уникальный ключ
        private List<Object> g; // Уникальный ключ
    }

    @Data
    public static class Image {
        private String id;
        private String mediaStorage;
        private String path;
        private boolean yamsStorage;
    }

    @Data
    public static class PaidServices {
        private Boolean halva;
        private Boolean highlight;
        private Boolean polepos;
        private Object ribbons; // Может быть null
    }

    @Data
    public static class ShowParameters {
        private boolean showCall;
        private boolean showChat;
        private boolean showImportLink;
        private boolean showWebShopLink;
    }
}
