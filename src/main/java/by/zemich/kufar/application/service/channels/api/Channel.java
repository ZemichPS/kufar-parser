package by.zemich.kufar.application.service.channels.api;

import by.zemich.kufar.application.service.api.AdvertisementPublisher;
import by.zemich.kufar.application.service.api.Notifiable;
import by.zemich.kufar.application.service.api.PhotoMessenger;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.model.Notification;
import by.zemich.kufar.domain.policy.api.Policy;
import by.zemich.kufar.application.service.PostManager;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.ArrayList;
import java.util.List;

public abstract class Channel implements AdvertisementPublisher, Notifiable {
    protected final List<Policy<Advertisement>> policies = new ArrayList<>();
    protected final PhotoMessenger<SendPhoto> photoMessenger;
    protected final PostManager postManager;

    protected Channel(PhotoMessenger<SendPhoto> photoMessenger, PostManager postManager) {
        this.photoMessenger = photoMessenger;
        this.postManager = postManager;
    }

    @Override
    public boolean publish(Advertisement advertisement) {
        boolean policyResult = policies.stream()
                .allMatch(policy -> policy.isSatisfiedBy(advertisement));
        if (!policyResult) return false;

        SendPhoto photoPost = postManager.createPhotoPostFromAd(advertisement);
        photoPost.setChatId(getChannelId());
        photoMessenger.sendPhoto(photoPost);
        return true;

    }

    public abstract String getChannelName();

    public abstract String getChannelId();

    public abstract String getNotifierId();

    @Override
    public void notify(Notification notification) {
        SendPhoto photoPost = postManager.createPostFromNotification(notification);
        photoPost.setChatId(getChannelId());
        photoMessenger.sendPhoto(photoPost);
    }
}
