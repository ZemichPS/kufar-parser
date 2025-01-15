package by.zemich.kufar.infrastructure.clients;

import by.zemich.kufar.application.service.AdvertisementService;
import by.zemich.kufar.infrastructure.clients.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
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
    private final String GET_CATEGORIES_URL = "https://api.kufar.by/category-tree/v1/category_tree";
    private final String BASE_ADS_FILTER_URL = "https://api.kufar.by/search-api/v2/search/rendered-paginated";

    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;
    private final ObjectMapper objectMapper;

    private final AdvertisementService advertisementService;

    public KufarClient(@Qualifier("priorityRestTemplate") RestTemplate restTemplate,
                       ObjectMapper objectMapper, RetryTemplate retryTemplate, AdvertisementService advertisementService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.retryTemplate = retryTemplate;
        this.advertisementService = advertisementService;
    }

    public AdsDTO getNewAdsByCategoryIdAndByLastSort(String categoryId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(ADVERTISEMENT_URL)
                .queryParam("cat", categoryId)
                .queryParam("lang", "17000")
                .queryParam("size", "100")
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

        return retryTemplate.execute( retryContext ->
                restTemplate.getForObject(fullAdDetailsUrl, AdDetailsDTO.class)
        );
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

    public List<ManufacturerDto> getFilledManufacture() {
        FilterDto filterDto = restTemplate.getForObject(FILTER_URL, FilterDto.class);

        List<FilterDto.RuleWrapper> ruleWrappers = filterDto.getMetadata().getParameters().getRules().stream()
                .filter(ruleWrapper -> ruleWrapper.getRule().getCategory() != null)
                .filter(ruleWrapper -> ruleWrapper.getRule().getCategory().equalsIgnoreCase("17010"))
                .toList();

        List<FilterDto.Ref> phonesBrand = filterDto.getMetadata().getParameters().getRefs().values().stream()
                .filter(ref1 -> ref1.getName().equalsIgnoreCase("phones_brand"))
                .toList();

        List<FilterDto.Ref> phonesModels = filterDto.getMetadata().getParameters().getRefs().values().stream()
                .filter(ref1 -> ref1.getName().equalsIgnoreCase("phones_model"))
                .toList();

        return ruleWrappers.stream()
                .map(
                        ruleWrapper -> {
                            String brandId = ruleWrapper.getRule().getPhonesBrand();
                            String brandName = phonesBrand.stream().flatMap(ref -> ref.getValues().stream())
                                    .filter(value -> value.getValue().equalsIgnoreCase(brandId))
                                    .map(value -> value.getLabels().get("ru"))
                                    .findFirst().orElse("");

                            List<ManufacturerDto.ModelDto> modelList = ruleWrapper.getRefs().stream()
                                    .flatMap(modelRefId -> phonesModels.stream().filter(ref -> ref.getVariationId().equals(modelRefId)))
                                    .flatMap(ref -> ref.getValues().stream())
                                    .map(value -> new ManufacturerDto.ModelDto(
                                            value.getValue(),
                                            value.getLabels().get("ru")
                                    ))
                                    .toList();
                            return new ManufacturerDto(Objects.nonNull(brandId) ? Long.parseLong(brandId) : 0, brandName, modelList);
                        }
                ).filter(manufacturer -> manufacturer.getId() != 0)
                .toList();
    }


    public AdsDTO getAdsByParameters(Map<String, String> paramMap) {
        String queryString = paramMap.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
        String url = BASE_ADS_FILTER_URL + "?" + queryString;
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return retryTemplate.execute(context ->
                restTemplate.getForObject(uri, AdsDTO.class)
        );
    }

    public CategoriesDto getCategories() {
        URI uri = UriComponentsBuilder.fromHttpUrl(GET_CATEGORIES_URL)
                .build().toUri();
        return restTemplate.getForObject(uri, CategoriesDto.class);
    }


}
