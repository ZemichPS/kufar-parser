package by.zemich.kufar.infrastructure.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "by.zemich.kufar.infrastructure.repository.jparepository")
public class DatasourceProdConfig {

    @Bean
    @Profile("prod")
    DataSource dataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/kufar_parser");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setSchema("app");
        return new HikariDataSource(config);
    }

    @Profile("test")
    @Bean
    DataSource testDataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/test_kufar_parser");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setSchema("app");
        return new HikariDataSource(config);
    }
}
