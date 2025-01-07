package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

public class OnlyBrandWoomanShoesPolicy implements Policy<Advertisement> {
    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return "8100".equalsIgnoreCase(advertisement.getCategory())
                && advertisement.getParameterValueByParameterName("women_shoes_brand").isPresent();
    }
}
