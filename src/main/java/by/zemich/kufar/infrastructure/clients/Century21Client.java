package by.zemich.kufar.infrastructure.clients;

import by.zemich.kufar.infrastructure.clients.dto.GetProductPage21vekRequest;
import by.zemich.kufar.infrastructure.clients.dto.Century21stGoodsPageDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class Century21Client {

    private final String REQUEST_FIRST_PAGE_URL = "https://gate.21vek.by/product-listings/api/v1/products";
    private final RestTemplate restTemplate;

    public Century21Client(@Qualifier("vek21restTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Century21stGoodsPageDTO getPage(GetProductPage21vekRequest request) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(REQUEST_FIRST_PAGE_URL)
                .queryParam("page", "1")
                .build().toUri();
        return restTemplate.postForObject(uri, request, Century21stGoodsPageDTO.class);
    }

}
