package by.zemich.kufar.utils;

import by.zemich.kufar.dao.entity.*;
import by.zemich.kufar.dto.AdsDTO;
import by.zemich.kufar.dto.CategoriesDto;
import by.zemich.kufar.dto.Century21stGoodsPageDTO;
import by.zemich.kufar.dto.GeoDataDTO;
import by.zemich.kufar.service.api.MarketService;
import by.zemich.kufar.service.clients.ManufacturerDto;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Mapper {
    public static Advertisement mapToEntity(AdsDTO.AdDTO source) {
        return Advertisement.builder()
                .adId(source.getAdId())
                .link(source.getAdLink())
                .images(source.getImages().stream()
                        .map(AdsDTO.ImageDTO::getPath)
                        .collect(Collectors.joining(";"))
                )
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

    public static Manufacturer mapToEntity(ManufacturerDto source) {
        return Manufacturer.builder()
                .id(source.getId())
                .name(source.getName())
                .models(new ArrayList<>())
                .build();
    }

    public static Model mapToEntity(ManufacturerDto.ModelDto source) {
        return Model.builder()
                .name(source.getName())
                .build();
    }


    public static MarketService.ProductDataDto mapToDto(Century21stGoodsPageDTO.ProductDTO source){
        return new MarketService.ProductDataDto(source.getLink(), source.getPrice());
    }

    public static Category mapToEntity(CategoriesDto.CategoryDto source) {
        return Category.builder()
                .id(source.getId())
                .name(source.getName())
                .version(source.getVersion())
                .ordered(source.getOrder())
                .subcategories(new ArrayList<>())
                .build();
    }

    public static Subcategory mapToEntity(CategoriesDto.CategoryDto.SubcategoryDto source) {
        return Subcategory.builder()
                .id(source.getId())
                .ordered(source.getOrder())
                .name(source.getName())
                .redirect(source.getRedirect())
                .build();
    }


}
