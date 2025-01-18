package by.zemich.kufar.application.service.channels;

import by.zemich.kufar.application.service.channels.api.TelegramChannel;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.*;
import by.zemich.kufar.application.service.AdvertisementService;
import by.zemich.kufar.application.service.TelegramPostManager;
import by.zemich.kufar.domain.policy.api.Policy;
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
    private final PriceAnalyzer priceAnalyzer;
    private final AdvertisementService advertisementService;

    public SmartphoneBestPriceChannel(PhotoMessenger<SendPhoto> messenger,
                                      TelegramPostManager telegramPostManager,
                                      PriceAnalyzer priceAnalyzer,
                                      AdvertisementService advertisementService
    ) {
        super(messenger, telegramPostManager);
        this.priceAnalyzer = priceAnalyzer;
        this.advertisementService = advertisementService;
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

    @Override
    protected List<Policy<Advertisement>> createPolicies() {
        return List.of(
                new OnlyOriginalGoodsPolicy(),
                new CategoryPolicy("17010"),
                new MinPercentagePolicy(
                        BigDecimal.valueOf(-35),
                        priceAnalyzer,
                        advertisementService
                ),
                // new OnlyOriginalGoodsPolicy().not(new FastSalesPolicy()),
                new OnlyCorrectModelPolicy());
    }
}
