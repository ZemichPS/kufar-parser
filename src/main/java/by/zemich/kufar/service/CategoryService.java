package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Category;
import by.zemich.kufar.dao.jparepository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> getById(String id) {
        return categoryRepository.findById(id);
    }
}
