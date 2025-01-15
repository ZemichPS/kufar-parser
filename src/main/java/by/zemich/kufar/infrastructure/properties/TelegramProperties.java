package by.zemich.kufar.infrastructure.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "telegram")
@Getter
@Setter
@AllArgsConstructor
public class TelegramProperties {
    private String token;
    private String name;
}
