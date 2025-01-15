package by.zemich.kufar.infrastructure.repository.jparepository;

import by.zemich.kufar.domain.model.Category;
import by.zemich.kufar.domain.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, String> {
    List<Subcategory> findAllByCategory(Category category);
}
