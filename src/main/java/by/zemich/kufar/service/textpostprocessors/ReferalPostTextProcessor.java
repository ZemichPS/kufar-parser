package by.zemich.kufar.service.textpostprocessors;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.MarketService;
import by.zemich.kufar.service.api.PostTextProcessor;
import by.zemich.kufar.service.clients.AdmitadClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//@Component
@RequiredArgsConstructor
public class ReferalPostTextProcessor implements PostTextProcessor {

    private final List<MarketService> marketServices;
    private final AdmitadClient admitadClient;

    @Override
    public String getLine(Advertisement advertisement) {
        return marketServices.stream()
                .map(marketService -> getReferalLine(marketService, advertisement))
                .collect(Collectors.joining("\n"));
    }

    private String getReferalLine(MarketService marketService, Advertisement advertisement) {
        MarketService.ProductDataDto productData = marketService.getProductDataByAdvertisement(advertisement);
        final String affiliatedLink = admitadClient.getAffiliatedLink(productData.linkOnProductPage());
        return "s% стоимость: s% %s".formatted(marketService.getMarketName(), productData.price(), affiliatedLink);
    }
}
