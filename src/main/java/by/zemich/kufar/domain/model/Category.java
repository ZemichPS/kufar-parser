package by.zemich.kufar.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories", schema = "app")
@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
@ToString(exclude = {"subcategories"})
public class Category{
    @Id
    private String id;
    private String name;
    private String version;
    private String ordered;
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "category"
    )
    @Setter(AccessLevel.NONE)
    private List<Subcategory> subcategories = new ArrayList<>();

    public void addSubcategory(Subcategory subcategory) {
        subcategory.addParent(this);
        subcategories.add(subcategory);
    }

    public void removeSubcategory(Subcategory subcategory) {
        subcategory.addParent(null);
        subcategories.remove(subcategory);
    }
}
