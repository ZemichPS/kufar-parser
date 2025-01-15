package by.zemich.kufar.infrastructure.clients.dto;

import by.zemich.kufar.infrastructure.config.PriceDeserializer;
import by.zemich.kufar.infrastructure.config.VlDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AdsDTO {

    private List<AdDTO> ads = new ArrayList<>();
    private PaginationDTO pagination;
    private long total;

    @Data
    public static class AdDTO {
        private String accountId;
        private List<AccountParameterDTO> accountParameters = new ArrayList<>();
        private long adId;
        private String adLink;
        private List<AdParameterDTO> adParameters = new ArrayList<>();
        private String body;
        private String bodyShort;
        private String category;
        private boolean companyAd;
        private String currency;
        private List<ImageDTO> images = new ArrayList<>();
        @JsonProperty("is_mine")
        private boolean isMine;
        private long listId;
        private LocalDateTime listTime;
        private String messageId;
        private PaidServicesDTO paidServices;
        private boolean phoneHidden;
        @JsonDeserialize(using = PriceDeserializer.class)
        private BigDecimal priceByn;
        @JsonDeserialize(using = PriceDeserializer.class)
        private BigDecimal priceUsd;
        private String remunerationType;
        private ShowParametersDTO showParameters;
        private String subject;
        private String type;
    }

    @Data
    public static class AccountParameterDTO {
        private String pl;
        private String vl;
        private String p;
        private String v;
        private String pu;
    }

    @Data
    public static class AdParameterDTO {
        private String pl;
        @JsonDeserialize(using = VlDeserializer.class)
        private String vl;
        private String p;
      //  @JsonDeserialize(using = PriceDeserializer.class)
        private Object v;
        private String pu;
    }

    @Data
    public static class ImageDTO {
        private String id;
        private String mediaStorage;
        private String path;
        private boolean yamsStorage;
    }

    @Data
    public static class PaidServicesDTO {
        private boolean halva;
        private boolean highlight;
        private boolean polepos;
        private Object ribbons;
    }

    @Data
    public static class ShowParametersDTO {
        private boolean showCall;
        private boolean showChat;
        private boolean showImportLink;
        private boolean showWebShopLink;
    }

    @Data
    public static class PaginationDTO {
        private List<PageDTO> pages = new ArrayList<>();
    }

    @Data
    public static class PageDTO {
        private String label;
        private int num;
        private String token;
    }
}
