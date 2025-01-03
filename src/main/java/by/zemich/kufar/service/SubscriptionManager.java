package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.entity.UserSubscription;
import by.zemich.kufar.dao.redisrepository.UserSubscriptionRepository;
import by.zemich.kufar.service.api.AdvertisementPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionManager implements AdvertisementPublisher {
    private final UserSubscriptionRepository subscriptionRepository;
    private final NotificationService notificationService;

    public void matchAndNotify(Advertisement advertisement) {
        subscriptionRepository.findAll().stream()
                .filter(subscription -> subscription.isSatisfied(advertisement))
                .map(UserSubscription::getSubscriberId)
                .forEach(userId-> notificationService.notifyMatchingAd(userId, advertisement));
    }

    public void subscribe(UserSubscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public void unsubscribe(UUID id) {
        subscriptionRepository.deleteById(id);
    }

    @Override
    public void publish(Advertisement advertisement) throws Exception {
        this.matchAndNotify(advertisement);
    }
}
