package by.zemich.kufar.domain.policy;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.api.Policy;

import java.math.BigDecimal;
import java.util.Map;

public class WomenClothingPricePolicy implements Policy<Advertisement> {

    private final Map<String, BigDecimal> pricesMap;

    public WomenClothingPricePolicy(Map<String, BigDecimal> pricesMap) {
        this.pricesMap = pricesMap;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        if (!isApplicable(advertisement)) return false;
        BigDecimal currentPriceOfClothing = advertisement.getPriceInByn();

        return advertisement.getParameterByIdentity("women_clothes_type")
                .map(Advertisement.Parameter::getValue)
                .map(String::toLowerCase)
                .map(clothingType -> pricesMap.getOrDefault(clothingType, new BigDecimal(50)))
                .map(maxPrice -> currentPriceOfClothing.compareTo(maxPrice) <= 0)
                .orElse(false);
    }

    private boolean isApplicable(Advertisement advertisement) {
        return advertisement.getParameterByIdentity("women_clothes_type").isPresent();
    }
}
