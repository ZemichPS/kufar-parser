package by.zemich.kufar.infrastructure.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProductPage21vekRequest {
    private int templateId;
    private List<AttributeDTO> attributes;
    private List<String> discountTypes;
    private String deliveryType;
    private int page = 1;
    private List<String> producerIds;
    private String sortDirection;
    private String sortField;

    @Data
    public static class AttributeDTO {
        private Integer id;
        private List<Integer> values;
    }
}
