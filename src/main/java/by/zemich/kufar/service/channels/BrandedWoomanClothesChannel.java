package by.zemich.kufar.service.channels;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.impl.ExcludedWomenClosesBrandPolicy;
import by.zemich.kufar.policies.impl.*;
import by.zemich.kufar.service.PostManager;
import by.zemich.kufar.service.api.Channel;
import by.zemich.kufar.service.api.PhotoMessenger;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.Thread.sleep;

//@Component
@Profile("prod")
public class BrandedWoomanClothesChannel extends Channel {

    private final String CHANNEL_CHAT_ID = "-1002270323996";
    private final String CHANNEL_CHAT_NANE = "Брендовая женская одежда";

    public BrandedWoomanClothesChannel(PhotoMessenger<SendPhoto> messenger,
                                       PostManager postManager
    ) {
        super(messenger, postManager);
        this.policies.addAll(
                List.of(
                        new OnlyOwnersAds(),
                        new OnlyOwnersAds(),
                        new OnlyOriginalGoodsPolicy(),
                        new OnlyBrandClothesPolicy().or(new OnlyBrandWoomanShoesPolicy()),
                        new OnlyDefiniteCategory("8110")
                                .or(new OnlyDefiniteCategory("8100"))
                                .or(new OnlyDefiniteCategory("8080"))
                                .or(new OnlyDefiniteCategory("8020")),
                        new MinPriceForNewGoodsPolicy(new BigDecimal(40)),
                        new ExcludedWomenClosesBrandPolicy(List.of(
                                "твое"
                        )).or( new ExcludedWomenShoesBrandPolicy(List.of(
                                "мегатоп"
                        )))

                )
        );

    }

    @Override
    public void publish(Advertisement advertisement) {

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
