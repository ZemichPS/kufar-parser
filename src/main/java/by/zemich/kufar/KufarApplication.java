package by.zemich.kufar;

import by.zemich.kufar.properties.MinioProperties;
import by.zemich.kufar.properties.TelegramProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        TelegramProperties.class,
        MinioProperties.class}
)
public class KufarApplication {

    public static void main(String[] args) {
        SpringApplication.run(KufarApplication.class, args);
    }

}
