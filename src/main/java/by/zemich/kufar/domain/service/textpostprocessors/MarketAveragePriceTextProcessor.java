package by.zemich.kufar.domain.service.textpostprocessors;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.model.ComputedPriceStatistics;
import by.zemich.kufar.application.service.AdvertisementServiceFacade;
import by.zemich.kufar.domain.service.PriceAnalyzer;
import by.zemich.kufar.domain.service.textpostprocessors.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
@Order(value = 6)
public class MarketAveragePriceTextProcessor implements PostTextProcessor {

    private final AdvertisementServiceFacade advertisementServiceFacade;
    private final PriceAnalyzer priceAnalyzer;

    @Override
    public String process(Advertisement advertisement) {

        BigDecimal currentAdPrice = advertisement.getPriceInByn();
        if (currentAdPrice.compareTo(BigDecimal.ZERO) == 0) return "";

        Optional<ComputedPriceStatistics> optionalComputedPriceStatistics = advertisementServiceFacade.getPriceStatisticsByModel(advertisement);
        if (optionalComputedPriceStatistics.isEmpty()) return "";
        ComputedPriceStatistics computedPriceStatistics = optionalComputedPriceStatistics.get();

        if (computedPriceStatistics.commonMarketPrice().compareTo(BigDecimal.ZERO) == 0 &&
                computedPriceStatistics.marketPriceForCommerce().compareTo(BigDecimal.ZERO) == 0 &&
                computedPriceStatistics.marketPriceForNotCommerce().compareTo(BigDecimal.ZERO) == 0
        ) return "";

        StringBuilder rezult = new StringBuilder("\uD83D\uDCC8 Средняя рыночная стоимость c учётом состояния и объёма памяти:\n");
        if (computedPriceStatistics.marketPriceForCommerce().compareTo(BigDecimal.ZERO) != 0) {
            rezult.append(" - %.0f (среди коммерческих объявлений). ".formatted(computedPriceStatistics.marketPriceForCommerce()));
            rezult.append("Разница %.0f%%; \n".formatted(priceAnalyzer.calculatePercentageDifference(computedPriceStatistics.marketPriceForCommerce(), currentAdPrice)));
        }

        if (computedPriceStatistics.marketPriceForNotCommerce().compareTo(BigDecimal.ZERO) != 0) {
            rezult.append(" - %.0f (среди не коммерческих объявлений). ".formatted(computedPriceStatistics.marketPriceForNotCommerce()));
            rezult.append("Разница %.0f%%; \n".formatted(priceAnalyzer.calculatePercentageDifference(computedPriceStatistics.marketPriceForNotCommerce(), currentAdPrice)));
        }

        if (computedPriceStatistics.commonMarketPrice().compareTo(BigDecimal.ZERO) != 0) {
            rezult.append(" - %.0f (среди коммерческих и не коммерческих объявлений). ".formatted(computedPriceStatistics.commonMarketPrice()));
            rezult.append("Разница %.0f%%;".formatted(priceAnalyzer.calculatePercentageDifference(computedPriceStatistics.commonMarketPrice(), currentAdPrice)));
        }

        return rezult.toString();
    }

    @Override
    public boolean isApplicable(Advertisement advertisement) {
        return advertisement.getBrand().isPresent() && advertisement.getModel().isPresent();
    }


}
