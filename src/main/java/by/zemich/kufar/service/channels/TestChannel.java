package by.zemich.kufar.service.channels;

import by.zemich.kufar.policies.impl.*;
import by.zemich.kufar.service.PostManager;
import by.zemich.kufar.service.api.Channel;
import by.zemich.kufar.service.api.PhotoMessenger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile("test")
public class TestChannel extends Channel {
    private final String CHANNEL_CHAT_ID = "-1002350825182";
    private final String CHANNEL_CHAT_NANE = "Выгодные объявления с Kufar";

    public TestChannel(PhotoMessenger<SendPhoto> messenger,
                       PostManager postManager
    ) {
        super(messenger, postManager);
        this.policies.addAll(
                List.of(
                        new OnlyOwnersAds(),
                        new OnlyOwnersAds(),
                        new OnlyOriginalGoods(),
                        new OnlyBrandClothesPolicy().or(new OnlyBrandWoomanShoesPolicy()),
                        new OnlyDefiniteCategory("8110")
                                .or(new OnlyDefiniteCategory("8100"))
                                .or(new OnlyDefiniteCategory("8080"))
                                .or(new OnlyDefiniteCategory("8020")),
                        new MinPriceForNewGoodsPolicy(new BigDecimal(40))
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
