package by.zemich.kufar.utils;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.entity.GeoData;
import by.zemich.kufar.dto.AdsDTO;
import by.zemich.kufar.dto.Century21stGoodsPageDTO;
import by.zemich.kufar.dto.GeoDataDTO;
import by.zemich.kufar.service.api.MarketService;

import java.util.ArrayList;

public class Mapper {
    public static Advertisement mapToEntity(AdsDTO.AdDTO source) {
        return Advertisement.builder()
                .adId(source.getAdId())
                .link(source.getAdLink())
                .category(source.getCategory())
                .companyAd(source.isCompanyAd())
                .publishedAt(source.getListTime())
                .subject(source.getSubject())
                .type(source.getType())
                .currency(source.getCurrency())
                .parameters(new ArrayList<>())
                .priceInByn(source.getPriceByn())
                .priceInUsd(source.getPriceUsd())
                .fullyFunctional(false)
                .build();
    }

    public static Advertisement.Parameter mapToEntity(AdsDTO.AdParameterDTO source) {
        return Advertisement.Parameter.builder()
                .identity(source.getP())
                .label(source.getPl())
                .value(source.getVl())
                .build();
    }

    public static GeoData mapToEntity(GeoDataDTO source) {
        return GeoData.builder()
                .id(source.getId())
                .pid(source.getPid())
                .name(source.getLabels().getRu())
                .type(source.getType())
                .tag(source.getTag())
                .region(source.getRegion())
                .area(source.getArea())
                .build();
    }

    public static MarketService.ProductDataDto mapToDto(Century21stGoodsPageDTO.ProductDTO source){
        return new MarketService.ProductDataDto(source.getLink(), source.getPrice());
    }
}
