package by.zemich.kufar.service.channels;

import by.zemich.kufar.policies.impl.OnlyCorrectModelPolicy;
import by.zemich.kufar.policies.impl.CategoryPolicy;
import by.zemich.kufar.policies.impl.MinPercentagePolicy;
import by.zemich.kufar.policies.impl.OnlyOriginalDevicesPolicy;
import by.zemich.kufar.service.AdvertisementService;
import by.zemich.kufar.service.PostManager;
import by.zemich.kufar.service.PriceAnalyzer;
import by.zemich.kufar.service.api.Channel;
import by.zemich.kufar.service.api.PhotoMessenger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.math.BigDecimal;
import java.util.List;

@Component
public class SmartphoneBestPriceChannel extends Channel {
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
                        new OnlyOriginalDevicesPolicy(),
                        new CategoryPolicy("17010"),
                        new MinPercentagePolicy(
                                BigDecimal.valueOf(-35),
                                priceAnalyzer,
                                advertisementService
                        ),
                        new OnlyCorrectModelPolicy()
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
