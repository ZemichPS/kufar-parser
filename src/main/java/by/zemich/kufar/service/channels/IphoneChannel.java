package by.zemich.kufar.service.channels;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.entity.Notification;
import by.zemich.kufar.policies.impl.*;
import by.zemich.kufar.service.PostManager;
import by.zemich.kufar.service.api.Channel;
import by.zemich.kufar.service.api.PhotoMessenger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.List;

//@Component
public class IphoneChannel extends Channel {

    private final String CHANNEL_CHAT_ID = "-1002317731094";
    private final String CHANNEL_CHAT_NANE = "Выгодные объявления с Kufar смартфонов Iphone";

    public IphoneChannel(PhotoMessenger<SendPhoto> messenger,
                         PostManager postManager) {
        super(messenger, postManager);

        this.policies.addAll(
                List.of(
                        new OnlyDefiniteBrandAndModelAdsPolicy(
                                "Apple",
                                List.of(
                                        "iPhone 14 Pro",
                                        "iPhone 13 Pro"
                                )
                        ),
                        new CategoryPolicy("17010"))
        );
        this.policies.add(new OnlyFullyFunctionalAdsPolicy());
        this.policies.add(new OnlyOriginalDevicesPolicy());
        this.policies.add(new SmartphoneMemoryCapacityAdsPolicy(List.of("256", "512")));
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
