package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.User;
import by.zemich.kufar.dao.jparepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> getByTelegramChatId(long chatId) {
        return userRepository.findByTelegramChatId(chatId);
    }

    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
