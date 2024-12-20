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
        List<BigDecimal> prices = advertisementService.getAllByBrandAndModel(advertisement.getBrand(), advertisement.getModel()).stream()
                .map(Advertisement::getPriceInByn)
                .filter(price -> price.compareTo(BigDecimal.ZERO) > 0)
                .toList();
        BigDecimal marketPrice = priceAnalyzer.getMarketPrice(prices);
        return advertisement.getPriceInByn().compareTo(marketPrice) >= 0;
    }
}
