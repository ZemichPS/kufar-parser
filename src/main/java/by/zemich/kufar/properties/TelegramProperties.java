package by.zemich.kufar.properties;

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
