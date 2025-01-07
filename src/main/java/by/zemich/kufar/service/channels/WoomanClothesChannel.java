package by.zemich.kufar.service.channels;

import by.zemich.kufar.policies.impl.IsChildCategoryPolicy;
import by.zemich.kufar.policies.impl.OnlyOwnersAds;
import by.zemich.kufar.service.PostManager;
import by.zemich.kufar.service.api.Channel;
import by.zemich.kufar.service.api.PhotoMessenger;
import by.zemich.kufar.service.textpostprocessors.SubCategoryService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.List;

@Component
public class WoomanClothesChannel extends Channel {

    private final String CHANNEL_CHAT_ID = "-1002490579807";
    private final String CHANNEL_CHAT_NANE = "Женская одежда";

    public WoomanClothesChannel(PhotoMessenger<SendPhoto> messenger,
                                PostManager postManager,
                                SubCategoryService subCategoryService
    ) {
        super(messenger, postManager);
        this.policies.addAll(
                List.of(
                        new IsChildCategoryPolicy("8000", subCategoryService),
                        new OnlyOwnersAds()
                )
        );
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
