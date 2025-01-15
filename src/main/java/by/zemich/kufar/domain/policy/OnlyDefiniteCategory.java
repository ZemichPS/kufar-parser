package by.zemich.kufar.domain.policy;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.api.Policy;

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
