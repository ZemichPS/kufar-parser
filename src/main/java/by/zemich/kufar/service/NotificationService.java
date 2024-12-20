package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.entity.User;
import by.zemich.kufar.service.api.PhotoMessenger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final UserService userService;
    private final PostManager postManager;
    private final PhotoMessenger<SendPhoto> telegramPhotoMessenger;

    public void notifyMatchingAd(UUID userId, Advertisement advertisement) {
        Long chatId = userService.getById(userId).map(User::getTelegramChatId).orElseThrow();
        SendPhoto adPost = postManager.createPhotoPostFromAd(advertisement);
        adPost.setChatId(chatId);
        telegramPhotoMessenger.sendPhoto(adPost);
    }
}
