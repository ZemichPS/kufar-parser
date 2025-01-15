package by.zemich.kufar.application.service.api;

import by.zemich.kufar.domain.model.Advertisement;

import java.math.BigDecimal;

public interface MarketService {
    ProductDataDto getProductDataByAdvertisement(Advertisement advertisement);
    String getMarketName();
    BigDecimal getProductPrice(Advertisement advertisement);

    record ProductDataDto(String linkOnProductPage, BigDecimal price){};
}
