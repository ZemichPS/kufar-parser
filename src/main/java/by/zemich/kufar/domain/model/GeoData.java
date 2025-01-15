package by.zemich.kufar.domain.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "geo")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class GeoData {
    @Id
    private Integer id;
    private Integer pid;
    private String name;
    private String type;
    private String tag;
    private Integer region;
    private Integer area;
}
