package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

@Service
@RequiredArgsConstructor
public class PostCreater {
    public SendPhoto createAddPostFromAd(Advertisement advertisement) {
        // TODO написать логику создания поста
        return new SendPhoto();
    }
}
