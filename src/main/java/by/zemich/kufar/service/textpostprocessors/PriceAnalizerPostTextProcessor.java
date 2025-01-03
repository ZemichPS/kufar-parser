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
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class PriceAnalizerPostTextProcessor implements PostTextProcessor {

    private final PriceAnalyzer priceAnalyzer;
    private final AdvertisementService advertisementService;
    private final MinimumRequredAmountOfDataForMarketPriceCountingPolicy minDataSize = new MinimumRequredAmountOfDataForMarketPriceCountingPolicy();

    @Override
    public String getLine(Advertisement advertisement) {
        if (!advertisement.isFullyFunctional()) return "";
        String brand = advertisement.getBrand();
        String model = advertisement.getModel();
        String memoryAmount = advertisement.getParameterValueByParameterName("phablet_phones_memory").orElse("");

        List<BigDecimal> prices;

        if (memoryAmount.isEmpty()) {
            return "";
        } else {
            long startTime = System.currentTimeMillis();
            prices = advertisementService.getAllByBrandAndModelWithMemoryAmount(brand, model, memoryAmount).stream()
                    .filter(Advertisement::isFullyFunctional)
                    .filter(adv -> adv.getCondition().equals(advertisement.getCondition()))
                    .map(Advertisement::getPriceInByn)
                    .toList();
            log.info("Время получения цены данной модели: {} millis", System.currentTimeMillis() - startTime);
        }

        if (!minDataSize.isSatisfiedBy(prices.size())) {
            return "";
        }

        BigDecimal marketPrice = priceAnalyzer.getMarketPrice(prices);
        return "\uD83D\uDCC8 %s: ".formatted(PostTextProcessor.getBoldHtmlStyle("Средняя рыночная стоимость (c учётом состояния и объёма памяти) ")) + marketPrice;
    }
}
