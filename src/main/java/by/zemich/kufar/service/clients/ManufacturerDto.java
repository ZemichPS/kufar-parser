package by.zemich.kufar.service.clients;

import lombok.Data;

import java.util.List;

@Data
public class ManufacturerDto {
    private String name;

    public ManufacturerDto(String name) {
        this.name = name;
    }

    private List<ModelDto> models;

    @Data
    public static class ModelDto {
        private String name;
    }
}
