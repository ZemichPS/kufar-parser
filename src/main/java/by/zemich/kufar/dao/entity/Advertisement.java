package by.zemich.kufar.dao.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "advertisements", schema = "app")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<Parameter> parameters = new ArrayList<>();

    public void addParameter(Parameter parameter) {
        parameters.add(parameter);
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

    public String getBrand(){
        return this.parameters.stream()
                .filter(param -> "brand".equals(param.identity))
                .map(param -> param.value)
                .findFirst().orElseThrow();
    }

    public String getModel(){
        return this.parameters.stream()
                .filter(param -> "model".equals(param.identity))
                .map(param -> param.value)
                .findFirst().orElseThrow();
    }

    public String getPhotoLink(){
        return "https://rms.kufar.by/v1/gallery/adim1/{filename.jpg}".replace("{filename.jpg}", this.link);
    }
}
