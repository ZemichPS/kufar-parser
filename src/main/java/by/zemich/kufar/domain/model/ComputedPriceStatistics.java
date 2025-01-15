package by.zemich.kufar.domain.model;

import java.math.BigDecimal;

public record ComputedPriceStatistics(BigDecimal marketPriceForCommerce,
                                      BigDecimal marketPriceForNotCommerce,
                                      BigDecimal commonMarketPrice
) {

}
