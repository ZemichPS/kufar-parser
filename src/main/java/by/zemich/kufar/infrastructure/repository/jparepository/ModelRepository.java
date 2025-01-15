package by.zemich.kufar.infrastructure.repository.jparepository;

import by.zemich.kufar.domain.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ModelRepository extends JpaRepository<Model, UUID> {
    Optional<Model> findByName(String name);

    boolean existsByName(String name);
}
