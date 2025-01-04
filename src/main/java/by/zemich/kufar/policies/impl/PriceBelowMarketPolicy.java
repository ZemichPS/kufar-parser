package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;
import by.zemich.kufar.service.AdvertisementService;
import by.zemich.kufar.service.PriceAnalyzer;

import java.math.BigDecimal;
import java.util.List;

public class PriceBelowMarketPolicy implements Policy<Advertisement> {
    private final PriceAnalyzer priceAnalyzer;
    private final AdvertisementService advertisementService;

    public PriceBelowMarketPolicy(PriceAnalyzer priceAnalyzer, AdvertisementService advertisementService) {
        this.priceAnalyzer = priceAnalyzer;
        this.advertisementService = advertisementService;
    }

    // TODO написать логику
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
