package by.zemich.kufar.dto;

import java.math.BigDecimal;

public record ComputedPriceStatistics(BigDecimal marketPriceForCommerce,
                                      BigDecimal marketPriceForNotCommerce,
                                      BigDecimal commonMarketPrice
) {

}
