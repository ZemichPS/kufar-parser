package by.zemich.kufar.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity(name = "parameters")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Parameter {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id", referencedColumnName = "id")
    private Advertisement advertisement;
    private String identity;
    private String label;
    private String value;
}
