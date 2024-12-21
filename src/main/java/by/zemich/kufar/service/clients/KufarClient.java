package by.zemich.kufar.service.clients;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dto.AdDetailsDTO;
import by.zemich.kufar.dto.AdsDTO;
import by.zemich.kufar.dto.FilterDto;
import by.zemich.kufar.dto.GeoDataDTO;
import by.zemich.kufar.service.AdvertisementService;
import by.zemich.kufar.utils.Mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class KufarClient {

    private final String ADVERTISEMENT_URL = "https://api.kufar.by/search-api/v2/search/rendered-paginated";
    private final String GEO_URL = "https://cre-api-v2.kufar.by/yandex-geocoder/static/regions";
    private final String AD_DETAILS_URL = "https://api.kufar.by/search-api/v2/item/{id}/rendered?lang=ru";
    private final String AD_PHOTO_URL = "https://rms.kufar.by/v1/gallery/adim1/{filename.jpg}";
    private final String FILTER_URL = "https://api.kufar.by/taxonomy-proxy/v1/dispatch?routing=web_generalist&parent=17000&application=ad_listing&platform=web";
    private final String GET_PAGE_BY_FILTER_URL = "https://api.kufar.by/search-api/v2/search/rendered-paginated?cat=17010&cmp=0&cnd=1&lang=ru&pb=5&sort=lst.d";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final AdvertisementService advertisementService;

    public KufarClient(@Qualifier("priorityRestTemplate") RestTemplate restTemplate,
                       ObjectMapper objectMapper, AdvertisementService advertisementService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.advertisementService = advertisementService;
    }

    public AdsDTO getNewAds() {
        URI uri = UriComponentsBuilder.fromHttpUrl(ADVERTISEMENT_URL)
                .queryParam("cat", "17010")
                .queryParam("lang", "17000")
                .queryParam("size", "40")
                .queryParam("sort", "lst.d")
                .build().toUri();
        return restTemplate.getForObject(uri, AdsDTO.class);
    }

    public List<GeoDataDTO> getGeoData() {

        String response = restTemplate.getForObject(GEO_URL, String.class);
        try {
            return objectMapper.readValue(response, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Failed to convert response. Error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public AdDetailsDTO getDetails(Long id) {
        String fullAdDetailsUrl = AD_DETAILS_URL.replace("{id}", id.toString());
        return restTemplate.getForObject(fullAdDetailsUrl, AdDetailsDTO.class);
    }

    public void getPhoto(String fileName) {
        String fullPhotoUrl = AD_PHOTO_URL.replace("{filename.jpg}", fileName);
    }

    public String getPhotoUrl(String fileName) {
        return AD_PHOTO_URL.replace("{filename.jpg}", fileName);
    }

    public FilterDto getFilters() {
        return restTemplate.getForObject(FILTER_URL, FilterDto.class);
    }

    public List<FilterDto.RuleWrapper> getFilledManufacture() {
        FilterDto filterDto = restTemplate.getForObject(FILTER_URL, FilterDto.class);

        FilterDto.Ref brandsRef = filterDto.getMetadata().getParameters().getRefs().values().stream()
                .filter(ref -> "phones_brand".equals(ref.getName())).findFirst().get();

        System.out.println("brands: %s".formatted(brandsRef));

        FilterDto.Ref modelsRef = filterDto.getMetadata().getParameters().getRefs().values().stream()
                .filter(ref -> "phones_model".equals(ref.getName())).findFirst().get();

        System.out.println("brands: %s".formatted(modelsRef));
        filterDto.getMetadata().getParameters().getRules().stream()
                .filter(ruleWrapper -> ruleWrapper.getRule().getCategory()!= null)
                .filter(ruleWrapper -> ruleWrapper.getRule().getCategory().equalsIgnoreCase("17010"))
                .filter(ruleWrapper -> )
                .toList();

        return null;

//        return filterDto.getMetadata().getParameters().getRules().stream()
//                .filter(ruleWrapper -> ruleWrapper.getRule().getCategory() != null)
//                .filter(ruleWrapper -> ruleWrapper.getRule().getCategory().equalsIgnoreCase("17010"))
//                .filter(ruleWrapper -> ruleWrapper.getRule().getPhonesBrand() != null)
//                .map(ruleWrapper -> {
//                    Integer phoneBrand = Integer.parseInt(ruleWrapper.getRule().getPhonesBrand());
//                    List<Integer> refs = ruleWrapper.getRefs().stream()
//                            .map(Integer::parseInt)
//                            .toList();
//                    return Map.entry(phoneBrand, refs);
//                })
//                .map(entry -> {
//                    Integer manufactureId = entry.getKey();
//                    String brandName = brandsRef.getValues().stream()
//                            .filter(value -> Integer.parseInt(value.getValue()) == manufactureId)
//                            .map(value -> value.getLabels().get("ru"))
//                            .findFirst().orElse("");
//
//                    List<ManufacturerDto.ModelDto> models = entry.getValue().stream()
//                            .map(id -> modelsRef.getValues().stream()
//                                    .filter(Objects::nonNull)
//                                    .filter(value -> Integer.parseInt(value.getValue()) == id)
//                                    .findFirst()
//                            ).filter(Optional::isPresent)
//                            .map(Optional::get)
//                            .map(value -> value.getLabels().get("ru"))
//                            .map(ManufacturerDto.ModelDto::new)
//                            .toList();
//                    return new ManufacturerDto(brandName, models);
//                }).toList();
    }

//    public List<ManufacturerDto> getManufacture() {
//        FilterDto filterDto = restTemplate.getForObject(FILTER_URL, FilterDto.class);
//
//        List<ManufacturerDto> manufacturerDtos = new ArrayList<>();
//        filterDto.getMetadata().getParameters().getRefs().values().stream()
//                .filter(ref -> "phones_brand".equals(ref.getName()))
//                .flatMap(ref -> ref.getValues().stream())
//                .map(value -> value.getLabels().get("ru"))
//                .map(ManufacturerDto::new)
//                .forEach(manufacturerDtos::add);
//
//        return manufacturerDtos;
//    }

    //
    public AdsDTO getAdsByModelAndPageNumber(String modelCategory, Integer pageSize) {
        URI uri = UriComponentsBuilder.fromHttpUrl(GET_PAGE_BY_FILTER_URL)
                .queryParam("phm", modelCategory)
                .queryParam("size", pageSize)
                .build().toUri();
        return restTemplate.getForObject(uri, AdsDTO.class);
    }


}
