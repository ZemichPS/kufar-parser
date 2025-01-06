package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.impl.MinimumRequredAmountOfDataForMarketPriceCountingPolicy;
import by.zemich.kufar.service.AdvertisementService;
import by.zemich.kufar.service.PriceAnalyzer;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
@Log4j2
public class MarketAveragePriceTextProcessor implements PostTextProcessor {

    protected final PriceAnalyzer priceAnalyzer;
    protected final AdvertisementService advertisementService;
    protected final MinimumRequredAmountOfDataForMarketPriceCountingPolicy minDataSize = new MinimumRequredAmountOfDataForMarketPriceCountingPolicy();

    protected final Predicate<Advertisement> fullFunctionalPredicate = Advertisement::isFullyFunctional;
    protected final Predicate<Advertisement> commerceAdPredicate = Advertisement::isCompanyAd;
    protected final Predicate<Advertisement> notCommerceAdPredicate = advertisement -> !advertisement.isCompanyAd();

    @Override
    public String process(Advertisement advertisement) {

        BigDecimal currentAdPrice = advertisement.getPriceInByn();

        if (!advertisement.isFullyFunctional()) return "";
        if (currentAdPrice.compareTo(BigDecimal.ZERO) == 0) return "";
        if (advertisement.getBrand().isEmpty() || advertisement.getModel().isEmpty()) return "";

        String brand = advertisement.getBrand().orElse("");
        String model = advertisement.getModel().orElse("");
        String memoryAmount = advertisement.getParameterValueByParameterName("phablet_phones_memory").orElse("");

        if (memoryAmount.isEmpty()) return "";

        long startWorkUnit = System.currentTimeMillis();
        List<Advertisement> advertisements = advertisementService.getAllByBrandAndModelWithMemoryAmount(brand, model, memoryAmount);

        log.info("Выборка данных согласно бренда и модели заняла: {}",
                System.currentTimeMillis() - startWorkUnit);

        if (!minDataSize.isSatisfiedBy(advertisements.size())) return "";

        BigDecimal marketPriceForCommerce = getMarketPrice(
                advertisements,
                fullFunctionalPredicate.and(commerceAdPredicate),
                advertisement.getCondition()
        );
        BigDecimal marketPriceForNotCommerce = getMarketPrice(
                advertisements,
                fullFunctionalPredicate.and(notCommerceAdPredicate),
                advertisement.getCondition()
        );
        BigDecimal commonMarketPrice = getMarketPrice(
                advertisements,
                fullFunctionalPredicate,
                advertisement.getCondition()
        );


        BigDecimal percentageDifferenceForCommon = priceAnalyzer.calculatePercentageDifference(commonMarketPrice, currentAdPrice);
        BigDecimal percentageDifferenceForCommerce = priceAnalyzer.calculatePercentageDifference(marketPriceForCommerce, currentAdPrice);
        BigDecimal percentageDifferenceForNotCommerce = priceAnalyzer.calculatePercentageDifference(marketPriceForNotCommerce, currentAdPrice);

        return "\uD83D\uDCC8 %s: ".formatted(PostTextProcessor.getBoldHtmlStyle(
                        """
                                Средняя рыночная стоимость c учётом состояния и объёма памяти:
                                 - %.0f (среди комерческих объявлений);
                                 - %.0f (среди не комерческих объявлений).
                                 - %.0f (среди комерческих и не комерческих объявлений).
                                Разница в сравнении с текущей ценой: %.2f%%, %.2f%%, %.2f%%.
                                """.formatted(
                                marketPriceForCommerce,
                                marketPriceForNotCommerce,
                                commonMarketPrice,
                                percentageDifferenceForCommerce,
                                percentageDifferenceForNotCommerce,
                                percentageDifferenceForCommon
                        )
                )
        );
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

        if(minDataSize.isSatisfiedBy(prices.size())) return BigDecimal.ZERO;
        return priceAnalyzer.getMarketPrice(prices);
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement.getBrand().isPresent() && advertisement.getModel().isPresent();
    }


}
