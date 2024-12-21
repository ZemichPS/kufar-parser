package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.impl.MinimumRequredAmountOfDataForMarketPriceCountingPolicy;
import by.zemich.kufar.service.AdvertisementService;
import by.zemich.kufar.service.PriceAnalyzer;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PriceAnalizerPostTextProcessor implements PostTextProcessor {

    private final PriceAnalyzer priceAnalyzer;
    private final AdvertisementService advertisementService;
    private final MinimumRequredAmountOfDataForMarketPriceCountingPolicy minDataSize = new MinimumRequredAmountOfDataForMarketPriceCountingPolicy();

    @Override
    public String getLine(Advertisement advertisement) {
        String brand = advertisement.getBrand();
        String model = advertisement.getModel();

        final List<BigDecimal> prices = advertisementService.getAllByBrandAndModel(brand, model).stream()
                .filter(Advertisement::isFullyFunctional)
                .map(Advertisement::getPriceInByn)
                .toList();

        if (!minDataSize.isSatisfiedBy(prices.size())) {
            return "";
        }

        BigDecimal marketPrice = priceAnalyzer.getMarketPrice(prices);
        return "Рыночная цена: " + marketPrice;
    }
}
