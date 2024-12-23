package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

public class OnlyDefiniteBrandAndModelAdsPolicy implements Policy<Advertisement> {

    private final String BRAND_NAME;
    private final String MODEL_NAME;

    public OnlyDefiniteBrandAndModelAdsPolicy(String brandName, String modelName) {
        BRAND_NAME = brandName;
        MODEL_NAME = modelName;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return advertisement.getBrand().equalsIgnoreCase(BRAND_NAME) && advertisement.getModel().equalsIgnoreCase(MODEL_NAME);
    }
}
