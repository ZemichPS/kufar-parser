package by.zemich.kufar.service.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OnlinerClient {
    private RestTemplate restTemplate;
}
