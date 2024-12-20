package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dto.Century21stGoodsPageDTO;
import by.zemich.kufar.dto.GetProductPage21vekRequest;
import by.zemich.kufar.service.api.MarketService;
import by.zemich.kufar.service.clients.Century21Client;
import by.zemich.kufar.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class Century21stCenturyMarketService implements MarketService {

    private final String MARKET_NAME = "21vek";
    private final Century21Client century21Client;

    @Override
    public ProductDataDto getProductDataByAdvertisement(Advertisement advertisement) {
        // TODO написать логику получения страницы товара
        GetProductPage21vekRequest request = new GetProductPage21vekRequest();
        Century21stGoodsPageDTO century21stGoodsPageDTO = century21Client.getPage(request);
        Century21stGoodsPageDTO.ProductDTO productDTO = century21stGoodsPageDTO.getData().getFirst();
        return Mapper.mapToDto(productDTO);
    }

    @Override
    public BigDecimal getProductPrice(Advertisement advertisement) {
        return null;
    }

    @Override
    public String getMarketName() {
        return this.MARKET_NAME;
    }
}
