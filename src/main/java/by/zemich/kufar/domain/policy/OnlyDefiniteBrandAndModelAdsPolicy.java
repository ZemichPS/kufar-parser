package by.zemich.kufar.domain.policy;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.api.Policy;

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
        String model = advertisement.getModel().orElse("");
        String brand = advertisement.getBrand().orElse("");
        return brand.equalsIgnoreCase(BRAND_NAME) &&  models.contains(model);
    }
}
