package by.zemich.kufar.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "notifications", schema = "app")
@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Notification {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;
    private String title;
    private String content;
    private String imageName;
    private LocalDateTime publishedAt;
}
