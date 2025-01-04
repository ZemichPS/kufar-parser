package by.zemich.kufar.service.channels;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.impl.*;
import by.zemich.kufar.service.PostManager;
import by.zemich.kufar.service.api.Channel;
import by.zemich.kufar.service.api.PhotoMessenger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.List;

@Component
public class ClothesChannel extends Channel {

    private final String CHANNEL_CHAT_ID = "-1002387076093";
    private final String CHANNEL_CHAT_NANE = "Мужская и женская одежда";

    public ClothesChannel(PhotoMessenger<SendPhoto> messenger,
                          PostManager postManager) {
        super(messenger, postManager);

        this.policies.add(
             new CategoryPolicy("8110").or(new CategoryPolicy("8080"))
        );
    }

    @Override
    public void publish(Advertisement advertisement) throws Exception {
        System.out.println("Я в методе publish ClothesChannel");
        super.publish(advertisement);
    }

    @Override
    public String getChannelName() {
        return this.CHANNEL_CHAT_NANE;
    }

    @Override
    public String getChannelId() {
        return this.CHANNEL_CHAT_ID;
    }

    @Override
    public String getNotifierId() {
        return CHANNEL_CHAT_ID;
    }


}
