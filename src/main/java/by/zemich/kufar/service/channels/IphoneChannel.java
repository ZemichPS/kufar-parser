package by.zemich.kufar.service.channels;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.impl.*;
import by.zemich.kufar.service.PostManager;
import by.zemich.kufar.service.api.Channel;
import by.zemich.kufar.service.api.PhotoMessenger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

@Component
public class IphoneChannel extends Channel {
    private final PostManager postManager;
    private final String CHANNEL_CHAT_ID = "-1002317731094";
    private final String CHANNEL_CHAT_NANE = "Выгодные объявления с Kufar смартфонов Iphone";


    public IphoneChannel(PhotoMessenger<SendPhoto> messenger,
                         PostManager postManager) {
        super(messenger);
        this.postManager = postManager;

        //this.policies.add(new OnlyDefiniteBrandAdsPolicy("Apple"));
        this.policies.add(new OnlyDefiniteBrandAndModelAdsPolicy("Apple", "iPhone 14 Pro"));
        this.policies.add(new OnlyFullyFunctionalAdsPolicy());
        this.policies.add(new OnlyOriginalDevicesPolicy());
        this.policies.add(new SmartphoneMemoryCapacityAdsPolicy("256"));
    }

    public void publish(Advertisement advertisement) throws Exception {
        boolean policyResult = policies.stream()
                .allMatch(policy -> policy.isSatisfiedBy(advertisement));
        if (!policyResult) return;

        SendPhoto photoPost = postManager.createPhotoPostFromAd(advertisement);
        photoPost.setChatId(getChannelChatId());
        photoMessenger.sendPhoto(photoPost);
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
