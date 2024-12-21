package by.zemich.kufar.service.api;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.ArrayList;
import java.util.List;

public abstract class Channel implements PostPublisher {
    protected final List<Policy<Advertisement>> policies = new ArrayList<>();
    protected final PhotoMessenger<SendPhoto> photoMessenger;

    protected Channel(PhotoMessenger<SendPhoto> photoMessenger) {
        this.photoMessenger = photoMessenger;
    }

    public abstract String getChannelName();

    public abstract String getChannelChatId();
}
