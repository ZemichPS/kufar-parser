package by.zemich.kufar.domain.policy;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.application.service.AdvertisementService;
import by.zemich.kufar.domain.service.PriceAnalyzer;
import by.zemich.kufar.domain.policy.api.Policy;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

public class MinPercentagePolicy implements Policy<Advertisement> {

    private final BigDecimal minPercentage;
    protected final PriceAnalyzer priceAnalyzer;
    protected final AdvertisementService advertisementService;
    protected final MinimumRequredAmountOfDataForMarketPriceCountingPolicy minDataSize = new MinimumRequredAmountOfDataForMarketPriceCountingPolicy();

    protected final Predicate<Advertisement> fullFunctionalPredicate = Advertisement::isFullyFunctional;


    public MinPercentagePolicy(BigDecimal minPercentage,
                               PriceAnalyzer priceAnalyzer,
                               AdvertisementService advertisementService
    ) {
        this.minPercentage = minPercentage;
        this.priceAnalyzer = priceAnalyzer;
        this.advertisementService = advertisementService;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        BigDecimal currentAdPrice = advertisement.getPriceInByn();

       // if (!advertisement.isFullyFunctional()) return false;
        if (currentAdPrice.compareTo(BigDecimal.ZERO) == 0) return false;
        if (advertisement.getBrand().isEmpty() || advertisement.getModel().isEmpty()) return false;

        String memoryAmount = advertisement.getParameterValueByParameterName("phablet_phones_memory").orElse("");
        if (memoryAmount.isEmpty()) return false;

        String brand = advertisement.getBrand().orElse("");
        String model = advertisement.getModel().orElse("");

        List<Advertisement> advertisements = advertisementService.getAllByBrandAndModelWithMemoryAmount(brand, model, memoryAmount);
        if (!minDataSize.isSatisfiedBy(advertisements.size())) return false;

        BigDecimal marketPriceForCommerce = getMarketPrice(
                advertisements,
                fullFunctionalPredicate,
                advertisement.getCondition()
        );

        BigDecimal percentageDifference = priceAnalyzer.calculatePercentageDifference(marketPriceForCommerce, currentAdPrice);
        return percentageDifference.compareTo(BigDecimal.ZERO) == -1
                && percentageDifference.compareTo(minPercentage) == -1;
    }


    protected BigDecimal getMarketPrice(List<Advertisement> advertisements,
                                        Predicate<Advertisement> predicate,
                                        String condition
    ) {
        List<BigDecimal> prices = advertisements.stream()
                .filter(predicate)
                .filter(ad -> ad.getCondition().equals(condition))
                .map(Advertisement::getPriceInByn)
                .toList();
        return priceAnalyzer.getMarketPrice(prices);
    }
}