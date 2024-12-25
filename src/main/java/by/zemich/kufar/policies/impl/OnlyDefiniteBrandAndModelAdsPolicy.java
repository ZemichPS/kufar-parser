package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

import java.util.List;

public class OnlyDefiniteBrandAndModelAdsPolicy implements Policy<Advertisement> {

    private final String BRAND_NAME;
    private final List<String> models;

    public OnlyDefiniteBrandAndModelAdsPolicy(String brandName, List<String> models) {
        BRAND_NAME = brandName;
        this.models = models;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        String model = advertisement.getModel();
        return advertisement.getBrand().equalsIgnoreCase(BRAND_NAME) &&  models.contains(model);
    }
}
