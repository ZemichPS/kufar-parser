package by.zemich.kufar.application.service.channels.api;

import by.zemich.kufar.application.service.TelegramPostManager;
import by.zemich.kufar.application.service.api.PhotoMessenger;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.model.Notification;
import by.zemich.kufar.domain.policy.api.Policy;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.List;


public abstract class TelegramChannel extends Channel {
    private final PhotoMessenger<SendPhoto> photoMessenger;
    private final TelegramPostManager telegramPostManager;

    protected TelegramChannel(List<Policy<Advertisement>> policies,
                              PhotoMessenger<SendPhoto> photoMessenger,
                              TelegramPostManager telegramPostManager

    ) {
        super(policies);
        this.photoMessenger = photoMessenger;
        this.telegramPostManager = telegramPostManager;
    }

    @Override
    public boolean publish(Advertisement advertisement) {
        if (super.checkPolicies(advertisement)) return false;
        SendPhoto photoPost = telegramPostManager.createPhotoPostFromAd(advertisement);
        photoPost.setChatId(getChannelId());
        photoMessenger.sendPhoto(photoPost);
        return true;
    }

    @Override
    public void notify(Notification notification) {
        SendPhoto photoPost = telegramPostManager.createPostFromNotification(notification);
        photoPost.setChatId(getChannelId());
        photoMessenger.sendPhoto(photoPost);
    }
}
