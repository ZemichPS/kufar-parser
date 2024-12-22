package by.zemich.kufar.service.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerDto {
    private Long id;
    private String name;
    private List<ModelDto> models;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ModelDto {
        private String name;
    }
}
