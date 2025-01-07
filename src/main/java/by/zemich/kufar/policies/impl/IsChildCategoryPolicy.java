package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.entity.Subcategory;
import by.zemich.kufar.policies.api.Policy;
import by.zemich.kufar.service.SubCategoryService;

import java.util.Optional;

public class IsChildCategoryPolicy implements Policy<Advertisement> {

    private final String parentCategory;
    private final SubCategoryService subCategoryService;

    public IsChildCategoryPolicy(String parentCategory, SubCategoryService subCategoryService) {
        this.parentCategory = parentCategory;
        this.subCategoryService = subCategoryService;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        String advertisementCategory = advertisement.getCategory();
        Optional<Subcategory> subcategory = subCategoryService.findById(advertisementCategory);
        return subcategory.map(value -> value.getCategory().getId().equals(parentCategory)).orElse(false);
    }
}
