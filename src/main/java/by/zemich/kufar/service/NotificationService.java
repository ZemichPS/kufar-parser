package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.entity.User;
import by.zemich.kufar.dao.entity.UserSubscription;
import by.zemich.kufar.service.api.Messenger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final UserService userService;
    private final Messenger<SendMessage> messenger;
    private final List<UserSubscription> subscriptions = new ArrayList<>();

    public void notify(Advertisement advertisement){
        subscriptions.stream()
                .filter(subscription -> subscription.isSatisfied(advertisement))
                .flatMap(subscription-> userService.getById(subscription.getId()).stream())
                .forEach(this::notify);
    }

    public void subscribe(UserSubscription subscription) {
        subscriptions.add(subscription);
    }

    public void unsubscribe(UserSubscription subscription) {
        subscriptions.remove(subscription);
    }

    public void notify(User user){
        // TODO notify logic
        log.info("User {} will be notified", user.getUsername());
    }
}
