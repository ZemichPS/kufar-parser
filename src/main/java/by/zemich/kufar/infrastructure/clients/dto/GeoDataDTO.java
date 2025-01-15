package by.zemich.kufar.infrastructure.clients.dto;

import lombok.Data;

import java.util.List;

@Data
public class GeoDataDTO {
    private Integer id;
    private Integer pid;
    private Labels labels;
    private String type;
    private String tag;
    private List<Double> bbox;
    private Integer region;
    private Integer area;

    @Data
    public static class Labels {
        private String ru;
        private String ru_pred;
        private String by;
        private String by_pred;
    }
}
