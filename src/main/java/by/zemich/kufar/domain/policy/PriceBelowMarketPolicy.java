package by.zemich.kufar.domain.policy;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.application.service.AdvertisementService;
import by.zemich.kufar.domain.service.PriceAnalyzer;
import by.zemich.kufar.domain.policy.api.Policy;

import java.math.BigDecimal;
import java.util.List;

public class PriceBelowMarketPolicy implements Policy<Advertisement> {
    private final PriceAnalyzer priceAnalyzer;
    private final AdvertisementService advertisementService;

    public PriceBelowMarketPolicy(PriceAnalyzer priceAnalyzer, AdvertisementService advertisementService) {
        this.priceAnalyzer = priceAnalyzer;
        this.advertisementService = advertisementService;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        BigDecimal productPrice = advertisement.getPriceInByn();

        if(advertisement.getModel().isEmpty()) return false;
        if(advertisement.getBrand().isEmpty()) return false;

        String model = advertisement.getModel().get();
        String brand = advertisement.getBrand().get();

        List<BigDecimal> prices = advertisementService.getAllByBrandAndModel(brand, model).stream()
                .map(Advertisement::getPriceInByn)
                .filter(price -> price.compareTo(BigDecimal.ZERO) > 0)
                .toList();
        try {
            BigDecimal marketPrice = priceAnalyzer.getMarketPrice(prices);
            return marketPrice.compareTo(productPrice) >= 0;
        } catch (Exception e) {
            return false;
        }
    }
}
