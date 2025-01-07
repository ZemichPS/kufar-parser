package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Subcategory;
import by.zemich.kufar.dao.jparepository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubcategoryRepository subCategoryRepository;

    public Optional<Subcategory> findById(String id) {
        return subCategoryRepository.findById(id);
    }
}
