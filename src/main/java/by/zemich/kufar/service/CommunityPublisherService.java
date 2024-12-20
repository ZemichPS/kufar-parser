package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;
import by.zemich.kufar.policies.impl.PriceBelowMarketPolicy;
import by.zemich.kufar.service.api.PhotoMessenger;
import by.zemich.kufar.service.api.PostPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

@Service
@RequiredArgsConstructor
public class CommunityPublisherService implements PostPublisher {
    private final PhotoMessenger<SendPhoto> photoMessenger;
    private final PostManager postManager;
    private final PriceAnalyzer priceAnalyzer;
    private final AdvertisementService advertisementService;
    private final String CHANNEL_CHAT_ID = "-1002385506241";

    public void publish(Advertisement advertisement) throws Exception {
        Policy<Advertisement> priceBelowMarketPolicy = new PriceBelowMarketPolicy(priceAnalyzer, advertisementService);
        if (!priceBelowMarketPolicy.isSatisfiedBy(advertisement)) return;

        SendPhoto photoPost = postManager.createPhotoPostFromAd(advertisement);
        photoPost.setChatId(CHANNEL_CHAT_ID);
        photoMessenger.sendPhoto(photoPost);
    }

}
