package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

public class OnlyBrandPolicy implements Policy<Advertisement> {
    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return advertisement.getParameterValueByParameterName("women_clothes_brand").isPresent();
    }
}
