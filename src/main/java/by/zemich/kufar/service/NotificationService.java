package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.entity.Notification;
import by.zemich.kufar.dao.entity.User;
import by.zemich.kufar.dao.jparepository.NotificationRepository;
import by.zemich.kufar.service.api.Notifiable;
import by.zemich.kufar.service.api.PhotoMessenger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final PostManager postManager;
    private final PhotoMessenger<SendPhoto> telegramPhotoMessenger;
    private final List<Notifiable> notifiable;

    public void notifyMatchingAd(UUID userId, Advertisement advertisement) {
        Long chatId = userService.getById(userId).map(User::getTelegramChatId).orElseThrow();
        SendPhoto adPost = postManager.createPhotoPostFromAd(advertisement);
        adPost.setChatId(chatId);
        telegramPhotoMessenger.sendPhoto(adPost);
    }

    public UUID notifyAll(Notification notification) {
        notificationRepository.save(notification);
        notifiable.forEach(
                notifiable -> notifiable.notify(notification)
        );
        notification.setPublishedAt(LocalDateTime.now());
        notificationRepository.save(notification);
        return notification.getId();
    }

    public UUID notifyByChannelId(Notification notification, String channelId) {
        //TODO написать логику
        return null;
    }

    public List<Notification> getAll(){
        return notificationRepository.findAll();
    }

    public Notification getById(UUID id) {
        // TODO создать исключение и выбрасывать его ??????
        return notificationRepository.findById(id).orElseThrow();
    }


}
