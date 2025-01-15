package by.zemich.kufar.infrastructure.clients;

import by.zemich.kufar.infrastructure.clients.dto.OnlinerProductPage;
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
