package by.zemich.kufar.service.clients;

import by.zemich.kufar.dto.GetProductPage21vekRequest;
import by.zemich.kufar.dto.GoodsPageDTO;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class Vek21Client {

    private final String REQUEST_FIRST_PAGE_URL = "https://gate.21vek.by/product-listings/api/v1/products";
    private final RestTemplate restTemplate;

    public Vek21Client(@Qualifier("vek21restTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    GoodsPageDTO getPage(GetProductPage21vekRequest request) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(REQUEST_FIRST_PAGE_URL)
                .queryParam("page", "1")
                .build().toUri();
        return restTemplate.postForObject(uri, request, GoodsPageDTO.class);
    }

}
