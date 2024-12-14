package by.zemich.kufar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

@Configuration
public class RestTemplateConfig {

    @Value("${rest-client.user-agent}")
    String useragent;

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder, ResponseErrorHandler errorHandler) {
        return builder
                .connectTimeout(Duration.ofSeconds(10))
                .readTimeout(Duration.ofSeconds(10))
                .defaultHeader("User-agent", useragent)
                .defaultHeader("content-type", "application/json")
                .errorHandler(errorHandler)
                .build();
    }

}
