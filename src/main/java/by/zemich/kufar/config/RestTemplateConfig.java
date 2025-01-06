package by.zemich.kufar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Value("${rest-client.user-agent}")
    String useragent;

    @Bean
    RestTemplate priorityRestTemplate(RestTemplateBuilder builder, ResponseErrorHandler errorHandler) {
        return builder
                .connectTimeout(Duration.ofSeconds(20))
                .readTimeout(Duration.ofSeconds(20))
                .defaultHeader("User-agent", useragent)
                .defaultHeader("content-type", "application/json")
                .errorHandler(errorHandler)
                .build();
    }

    @Bean
    RestTemplate vek21restTemplate(RestTemplateBuilder builder, ResponseErrorHandler errorHandler) {
        return builder
                .connectTimeout(Duration.ofSeconds(10))
                .readTimeout(Duration.ofSeconds(10))
                .defaultHeader("User-agent", useragent)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"")
                .defaultHeader("sec-ch-ua-mobile", "?0")
                .defaultHeader("sec-ch-ua-platform", "\"Windows\"")
                .errorHandler(errorHandler)
                .build();
    }



}
