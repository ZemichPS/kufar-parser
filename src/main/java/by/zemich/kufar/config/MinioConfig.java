package by.zemich.kufar.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("minio-root-user","minio-root-password")
                .build();


    }


}
