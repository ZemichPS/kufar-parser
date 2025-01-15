package by.zemich.kufar.application.service.channels;

import by.zemich.kufar.domain.policy.CategoryPolicy;
import by.zemich.kufar.domain.policy.OnlyOriginalGoodsPolicy;
import by.zemich.kufar.application.service.PostManager;
import by.zemich.kufar.application.service.api.PhotoMessenger;
import by.zemich.kufar.application.service.channels.api.Channel;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.List;

@Component
@Profile("prod")
public class MainChannel extends Channel {
    private final String CHANNEL_CHAT_ID = "-1002385506241";
    private final String CHANNEL_CHAT_NANE = "Выгодные объявления с Kufar";

    public MainChannel(PhotoMessenger<SendPhoto> messenger,
                       PostManager postManager
    ) {
        super(messenger, postManager);
        this.policies.addAll(
                List.of(
                        new OnlyOriginalGoodsPolicy(),
                        new CategoryPolicy("17010")
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
