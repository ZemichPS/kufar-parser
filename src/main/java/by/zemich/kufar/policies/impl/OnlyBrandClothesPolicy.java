package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

public class OnlyBrandClothesPolicy implements Policy<Advertisement> {
    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        if(advertisement.getParameterValueByParameterName("women_clothes_brand").isPresent()) {
            String brand = advertisement.getParameterValueByParameterName("women_clothes_brand").get();
            return !brand.equalsIgnoreCase("Другой") && !brand.isEmpty();
        }
        return false;
    }
}
