package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

@Service
@RequiredArgsConstructor
public class CommunityPublisherService implements PostPublisher {
    private final String channelChatId = "channelChatId";

    private final PostManager postManager;

    public void publish(Advertisement advertisement){
        SendPhoto photoPost = postManager.createPhotoPostFromAd(advertisement);

    }

}
