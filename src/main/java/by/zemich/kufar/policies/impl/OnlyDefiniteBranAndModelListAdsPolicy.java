package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

import java.util.List;

public class OnlyDefiniteBranAndModelListAdsPolicy implements Policy<Advertisement> {

    private final String BRAND_NAME;
    private final List<String> modelList;

    public OnlyDefiniteBranAndModelListAdsPolicy(String brandName, List<String> modelList) {
        BRAND_NAME = brandName;
        this.modelList = modelList;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        boolean modelFound = modelList.contains(advertisement.getModel());
        return advertisement.getBrand().equals(BRAND_NAME) && modelFound;
    }
}
