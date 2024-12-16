package by.zemich.kufar.service.clients;

import by.zemich.kufar.dto.AdsDTO;
import by.zemich.kufar.dto.GeoDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KufarClient {

    private final String ADVERTISEMENT_URL = "https://api.kufar.by/search-api/v2/search/rendered-paginated";
    private final String GEO_URL = "https://api.kufar.by/search-api/v2/search/rendered-paginated";

    private final RestTemplate restTemplate;

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
        return restTemplate.exchange(
                GEO_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GeoDataDTO>>() {
                }
        ).getBody();
    }


}
