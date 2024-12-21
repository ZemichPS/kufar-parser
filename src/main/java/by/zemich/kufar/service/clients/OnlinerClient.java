package by.zemich.kufar.service.clients;

import by.zemich.kufar.dto.OnlinerProductPage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OnlinerClient {
    private final RestTemplate restTemplate;

    public OnlinerClient(
            @Qualifier("priorityRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // TODO написать логику получения страницы
    public OnlinerProductPage getProductPage(String brand, String model) {
        return null;
    }
}
