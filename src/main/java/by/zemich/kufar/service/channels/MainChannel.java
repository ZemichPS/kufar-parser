package by.zemich.kufar.service.channels;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.entity.Notification;
import by.zemich.kufar.policies.impl.OnlyOriginalDevicesPolicy;
import by.zemich.kufar.service.PostManager;
import by.zemich.kufar.service.api.Channel;
import by.zemich.kufar.service.api.PhotoMessenger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

@Component
public class MainChannel extends Channel {
    private final String CHANNEL_CHAT_ID = "-1002385506241";
    private final String CHANNEL_CHAT_NANE = "Выгодные объявления с Kufar";

    public MainChannel(PhotoMessenger<SendPhoto> messenger,
                       PostManager postManager
    ) {
        super(messenger, postManager);
        this.policies.add(new OnlyOriginalDevicesPolicy());
    }

    @Override
    public String getChannelName() {
        return this.CHANNEL_CHAT_NANE;
    }

    @Override
    public String getChannelChatId() {
        return this.CHANNEL_CHAT_ID;
    }

}
