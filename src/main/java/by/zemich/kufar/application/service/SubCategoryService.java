package by.zemich.kufar.application.service;

import by.zemich.kufar.domain.model.Subcategory;
import by.zemich.kufar.infrastructure.repository.jparepository.SubcategoryRepository;
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
