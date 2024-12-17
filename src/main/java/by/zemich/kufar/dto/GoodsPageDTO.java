package by.zemich.kufar.dto;

import lombok.Data;

import java.util.List;

@Data
public class GoodsPageDTO {
    private List<ProductDTO> data;

    @Data
    public static class ProductDTO {
        private Long code;
        private String fullName;
        private String link;
        private Integer rating;
        private String preview;
        private Double price;
        private Double oldPrice;
        private List<String> discountTypes;
        private Integer promoDiscount;
        private String status;
        private Integer multiple;
        private ProducerDTO producer;
        private Integer reviewCount;
        private GalleryDTO gallery;
        private List<String> sales;
    }

    @Data
    public static class ProducerDTO {
        private String id;
        private String name;
    }

    @Data
    public static class GalleryDTO {
        private List<ImageDTO> images;
    }

    @Data
    public static class ImageDTO {
        private long id;
        private String url;
        private int position;
        private boolean isMain;
    }
}