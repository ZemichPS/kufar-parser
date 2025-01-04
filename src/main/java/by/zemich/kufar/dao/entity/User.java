package by.zemich.kufar.dao.entity;

import by.zemich.kufar.service.api.Notifiable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "users", schema = "app")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDateTime registerAt;
    private Long telegramChatId;
}
