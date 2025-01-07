package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

public class OnlyDefiniteCategory implements Policy<Advertisement> {

    private final String category;

    public OnlyDefiniteCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return category.equalsIgnoreCase(advertisement.getCategory());
    }
}
