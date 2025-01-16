package by.zemich.kufar.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.time.Duration;

@Configuration
@EnableRedisRepositories(basePackages = "by.zemich.kufar.infrastructure.repository.redisrepository")
public class RedisConnectionConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(configuration(), clientConfiguration());
    }

    RedisStandaloneConfiguration configuration() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost"); // заменить на in-memory-db из docker compose (название сервиса)
        configuration.setPort(6379);

        return configuration;
    }

    LettuceClientConfiguration clientConfiguration() {
        return LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(2))
                .shutdownTimeout(Duration.ZERO)
                .build();
    }
}

