package by.zemich.kufar.domain.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "advertisements", schema = "app")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
@EqualsAndHashCode
@ToString(exclude = "parameters")
public class Advertisement {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;
    private Long adId;
    private String link;
    private String category;
    private boolean companyAd;
    private String currency;
    private LocalDateTime publishedAt;
    private String subject;
    private String type;
    private BigDecimal priceInByn;
    private BigDecimal priceInUsd;
    private String details;
    private boolean fullyFunctional;
    private String images;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<Parameter> parameters = new ArrayList<>();

    public Advertisement addParameter(Parameter parameter) {
        parameters.add(parameter);
        return this;
    }

    public Advertisement setPriceInByn(BigDecimal priceInByn) {
        this.priceInByn = priceInByn;
        return this;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Parameter {
        public Parameter(String identity, String value) {
            this.identity = identity;
            this.value = value;
        }

        private String identity;
        private String value;
        private String label;
    }

    public Optional<String> getBrand() {
        return this.parameters.stream()
                .filter(param -> "phones_brand".equals(param.identity))
                .map(param -> param.value)
                .findFirst();
    }

    public Optional<String> getModel() {
        return this.parameters.stream()
                .filter(param -> "phones_model".equals(param.identity))
                .map(param -> param.value)
                .findFirst();
    }

    public Optional<String> getParameterValueByParameterName(String parameterName) {
        return this.parameters.stream()
                .filter(param -> parameterName.equals(param.identity))
                .map(param -> param.value)
                .findFirst();
    }

    public Optional<Parameter> getParameterByIdentity(String identity) {
        return this.parameters.stream()
                .filter(param -> identity.equals(param.identity))
                .findFirst();
    }

    public String getFullAddress() {
        String region = this.parameters.stream()
                .filter(param -> "region".equals(param.identity))
                .map(param -> param.value)
                .findFirst()
                .orElse("");

        String area = this.parameters.stream()
                .filter(param -> "area".equals(param.identity))
                .map(param -> param.value)
                .findFirst()
                .orElse("");

        return area + ", " + region;
    }

    public String getCondition(){
        return this.parameters.stream()
                .filter(param -> "condition".equals(param.identity))
                .map(param -> param.value)
                .findFirst()
                .orElse("");
    }

    public List<String> getLinks(){
        return Arrays.stream(this.images.split(";")).toList();
    }

    public Optional<String> getPhotoLink() {
        if (images.isEmpty()) return Optional.empty();

        String imageFilePath;
        if(images.contains(";")) {
            imageFilePath =  this.images.split(";")[0];
        } else imageFilePath = images;

        String url = "https://rms.kufar.by/v1/gallery/{filename.jpg}".replace("{filename.jpg}", imageFilePath);
        return Optional.of(url);
    }
}
