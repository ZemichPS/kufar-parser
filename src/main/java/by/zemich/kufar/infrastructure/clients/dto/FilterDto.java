package by.zemich.kufar.infrastructure.clients.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FilterDto {
    private Metadata metadata;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Metadata {
        private Parameters parameters;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Parameters {
        private String dispatchType;
        private String deduplicationKey;
        private Map<String, Ref> refs;
        private List<RuleWrapper> rules;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Ref {
        private String variationId;
        private String name;
        private String urlName;
        private boolean required;
        private String type;
        private String meta;
        private boolean multi;
        private Hint hint;
        private List<Action> actions;
        private Object grouping;
        private Labels labels;
        private List<Value> values;
        private String externalValuesUrl;
        private Range range;
        private int minTaxonomyVersion;
        private String imageUrl;
        @JsonProperty("is_type")
        private boolean isType;
    }

    @Data
    public static class UIComponent {
        private String fallbackType;
        private String type;
    }

    @Data
    public static class Hint {
        private String ru;
        private String by;
    }

    @Data
    public static class Action {
        private int actionId;
        private Object args;
        private Object dependsOn;
        private String name;
        private String type;
    }

    @Data
    public static class Labels {
        private Map<String, String> name;
        private Map<String, String> placeholder;
    }

    @Data
    public static class Value {
        private String value;
        private Map<String, String> labels;
        private String hint;
        private String imageUrl;
    }

    @Data
    public static class Range {
        private String defaultLower;
        private String defaultUpper;
        private String lower;
        private String upper;
        private String step;
    }

    @Data
    public static class RuleWrapper {
        private Rule rule;
        private List<String> refs;
    }

    @Data
    public static class Rule {
        private String region;
        private String category;
        private String phonesBrand;
        private String companyAd;
        private String phabletSmartWatchesType;
    }


}
