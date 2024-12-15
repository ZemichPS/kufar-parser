package by.zemich.kufar.utils;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.entity.Parameter;
import by.zemich.kufar.dto.AdsResponseDTO;

import java.util.ArrayList;

public class Mapper {
    public static Advertisement mapToEntity(AdsResponseDTO.AdDTO dto) {
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

    public static Parameter mapToEntity(AdsResponseDTO.AdParameterDTO dto) {
        return Parameter.builder()
                .identity(dto.getP())
                .label(dto.getPl())
                .value(dto.getVl())
                .build();
    }
}
