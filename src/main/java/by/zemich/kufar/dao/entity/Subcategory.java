package by.zemich.kufar.dao.entity;

import by.zemich.kufar.dto.CategoriesDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(schema = "app", name = "subcategories")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
@ToString(exclude = {"category"})
public class Subcategory {
    @Id
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent", referencedColumnName = "id")
    private Category category;
    private String ordered;
    private String name;
    private String redirect;

    public void addParent(Category category) {
        this.category = category;
    }
}
