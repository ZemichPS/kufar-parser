package by.zemich.kufar.dao.jparepository;

import by.zemich.kufar.dao.entity.Category;
import by.zemich.kufar.dao.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, String> {
    List<Subcategory> findAllByCategory(Category category);
}
