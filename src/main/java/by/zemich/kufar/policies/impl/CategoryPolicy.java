package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

public class CategoryPolicy implements Policy<Advertisement> {
    private final String categoryId;

    public CategoryPolicy(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return advertisement.getCategory().equals(categoryId);
    }
}
