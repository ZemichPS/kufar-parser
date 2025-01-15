package by.zemich.kufar.infrastructure.clients;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor @NoArgsConstructor @Builder
public class ManufacturerDto {
    private Long id;
    private String name;
    private List<ModelDto> models;

    @Data
    @EqualsAndHashCode
    @ToString
    @AllArgsConstructor @NoArgsConstructor @Builder
    public static class ModelDto {
        private String kufarId;
        private String name;
    }
}
