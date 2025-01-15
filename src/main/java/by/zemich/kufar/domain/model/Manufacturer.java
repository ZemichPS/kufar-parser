package by.zemich.kufar.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
@ToString(exclude = "models")
@EqualsAndHashCode(exclude = "models")
@Table(schema = "app", name = "manufactures")
public class Manufacturer {
    @Id
    private Long id;
    private String name;
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "manufacturer",
            orphanRemoval = true
    )
    private List<Model> models;

    public void addModel(Model model) {
        model.setManufacturer(this);
        models.add(model);
    }
    public void removeModel(Model model) {
        model.setManufacturer(null);
        models.remove(model);
    }
}
