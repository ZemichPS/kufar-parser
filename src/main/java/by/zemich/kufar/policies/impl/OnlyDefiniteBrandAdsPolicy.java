package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

public class OnlyDefiniteBrandAdsPolicy implements Policy<Advertisement> {

    private final String BRAND_NAME;

    public OnlyDefiniteBrandAdsPolicy(String brandName) {
        BRAND_NAME = brandName;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return advertisement.getBrand().equals(BRAND_NAME);
    }
}
