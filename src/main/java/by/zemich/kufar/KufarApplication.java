package by.zemich.kufar;

import by.zemich.kufar.infrastructure.properties.MinioProperties;
import by.zemich.kufar.infrastructure.properties.TelegramProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableConfigurationProperties({
        TelegramProperties.class,
        MinioProperties.class}
)
@EnableDiscoveryClient
@EnableCaching
@SpringBootApplication
public class KufarApplication {

    public static void main(String[] args) {
        SpringApplication.run(KufarApplication.class, args);
    }

}
