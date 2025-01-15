package by.zemich.kufar.infrastructure.clients.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OnlinerProductPage {
    private PriceRange priceRange;
    private int producersTotal;
    private ProducerInfo producers;
    private List<Producer> selectedProducers;
    private DiscountTypeInfo discountTypes;
    private List<DiscountType> selectedDiscountTypes;
    private Map<String, AttributeValues> attributeValues;
    private Map<String, List<AttributeValue>> selectedAttributeValues;
    private List<Integer> boolAttributes;
    private Map<String, String> deliveryTypes;

    @Data
    public static class PriceRange {
        private int min;
        private int max;
    }

    @Data
    public static class ProducerInfo {
        private List<Producer> values;
        private boolean expandable;
    }

    @Data
    public static class Producer {
        private String id;
        private String name;
    }

    @Data
    public static class DiscountTypeInfo {
        private List<DiscountType> values;
        private boolean expandable;
    }

    @Data
    public static class DiscountType {
        private String id;
        private String name;
    }

    @Data
    public static class AttributeValues {
        private List<AttributeValue> values;
        private boolean expandable;
    }

    @Data
    public static class AttributeValue {
        private int id;
        private String name;
        private String key;
    }

}
