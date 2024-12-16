package by.zemich.kufar.utils;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.entity.GeoData;
import by.zemich.kufar.dao.entity.Parameter;
import by.zemich.kufar.dto.AdsDTO;
import by.zemich.kufar.dto.GeoDataDTO;

import java.util.ArrayList;

public class Mapper {
    public static Advertisement mapToEntity(AdsDTO.AdDTO dto) {
        return Advertisement.builder()
                .adId(dto.getAdId())
                .link(dto.getAdLink())
                .category(dto.getCategory())
                .companyAd(dto.isCompanyAd())
                .publishedAt(dto.getListTime())
                .subject(dto.getSubject())
                .type(dto.getType())
                .currency(dto.getCurrency())
                .parameters(new ArrayList<>())
                .priceInByn(dto.getPriceByn())
                .priceInUsd(dto.getPriceUsd())
                .build();
    }

    public static Parameter mapToEntity(AdsDTO.AdParameterDTO dto) {
        return Parameter.builder()
                .identity(dto.getP())
                .label(dto.getPl())
                .value(dto.getVl())
                .build();
    }

    public static GeoData mapToEntity(GeoDataDTO dto) {
        return GeoData.builder()
                .id(dto.getId())
                .pid(dto.getPid())
                .name(dto.getLabels().getRu())
                .type(dto.getType())
                .tag(dto.getTag())
                .region(dto.getRegion())
                .area(dto.getArea())
                .build();
    }
}
