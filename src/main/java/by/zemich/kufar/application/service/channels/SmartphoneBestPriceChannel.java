package by.zemich.kufar.application.service.channels;

import by.zemich.kufar.application.service.channels.api.TelegramChannel;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.*;
import by.zemich.kufar.application.service.channels.api.Channel;
import by.zemich.kufar.application.service.AdvertisementService;
import by.zemich.kufar.application.service.PostManager;
import by.zemich.kufar.domain.service.PriceAnalyzer;
import by.zemich.kufar.application.service.api.PhotoMessenger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.Thread.sleep;

@Component
@Profile("prod")
public class SmartphoneBestPriceChannel extends TelegramChannel {
    private final String CHANNEL_CHAT_ID = "-1002367745711";
    private final String CHANNEL_CHAT_NANE = "Лушие цены на смартфоны c куфар";

    public SmartphoneBestPriceChannel(PhotoMessenger<SendPhoto> messenger,
                                      PostManager postManager,
                                      PriceAnalyzer priceAnalyzer,
                                      AdvertisementService advertisementService
    ) {
        super(messenger, postManager);
        this.policies.addAll(
                List.of(
                 //       new OnlyOriginalGoodsPolicy().not(new FastSalesPolicy()),
                        new OnlyOriginalGoodsPolicy(),
                        new CategoryPolicy("17010"),
                        new MinPercentagePolicy(
                                BigDecimal.valueOf(-35),
                                priceAnalyzer,
                                advertisementService
                        ),
                        new OnlyCorrectModelPolicy()
                        //new OnlyFullyFunctionalAdsPolicy()
                )
        );
    }

    @Override
    public boolean publish(Advertisement advertisement) {
        return super.publish(advertisement);
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
