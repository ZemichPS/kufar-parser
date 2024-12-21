package by.zemich.kufar.service.channels;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.impl.PriceBelowMarketPolicy;
import by.zemich.kufar.service.AdvertisementService;
import by.zemich.kufar.service.PostManager;
import by.zemich.kufar.service.PriceAnalyzer;
import by.zemich.kufar.service.api.Channel;
import by.zemich.kufar.service.api.PhotoMessenger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

@Component
public class MainChannel extends Channel {
    private final PostManager postManager;
    private final String CHANNEL_CHAT_ID = "-1002385506241";
    private final String CHANNEL_CHAT_NANE = "Выгодные объявления с Kufar";


    public MainChannel(PhotoMessenger<SendPhoto> messenger,
                       PostManager postManager,
                       PriceAnalyzer priceAnalyzer,
                       AdvertisementService advertisementService) {
        super(messenger);
        this.postManager = postManager;
        this.policies.add(new PriceBelowMarketPolicy(priceAnalyzer, advertisementService));
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
