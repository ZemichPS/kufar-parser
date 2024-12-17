package by.zemich.kufar.dao.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter @Setter
@ToString(exclude = "models")
@EqualsAndHashCode(exclude = "models")
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
