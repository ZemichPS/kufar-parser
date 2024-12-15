package by.zemich.kufar.dao.repository;

import by.zemich.kufar.dao.entity.User;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Override
    Optional<User> findById(UUID id);

    Optional<User> findByTelegramChatId(Long chatId);
}
