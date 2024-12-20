package by.zemich.kufar.service.api;

import by.zemich.kufar.dao.entity.Advertisement;

import java.math.BigDecimal;

public interface MarketService {
    ProductDataDto getProductDataByAdvertisement(Advertisement advertisement);
    String getMarketName();
    BigDecimal getProductPrice(Advertisement advertisement);

    record ProductDataDto(String linkOnProductPage, BigDecimal price){};
}
