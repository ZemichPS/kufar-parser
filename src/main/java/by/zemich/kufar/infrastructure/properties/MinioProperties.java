package by.zemich.kufar.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio")
@Getter @Setter
public class MinioProperties {
   private String notificationImageBucketName;
}
