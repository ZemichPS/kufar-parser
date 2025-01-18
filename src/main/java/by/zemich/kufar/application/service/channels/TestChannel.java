package by.zemich.kufar.application.service.channels;

import by.zemich.kufar.application.service.api.CategoryPriceListRepository;
import by.zemich.kufar.application.service.api.ClothesBrandsRepository;
import by.zemich.kufar.application.service.channels.api.TelegramChannel;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.*;
import by.zemich.kufar.application.service.TelegramPostManager;
import by.zemich.kufar.application.service.api.PhotoMessenger;
import by.zemich.kufar.domain.policy.api.Policy;
import by.zemich.kufar.domain.service.PolicyChecker;
import by.zemich.kufar.infrastructure.repository.filerepositories.CategoryPricelistFileRepository;
import by.zemich.kufar.infrastructure.repository.filerepositories.ClothesBrandsFileRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Profile("test")
public class TestChannel extends TelegramChannel {
    private final String CHANNEL_CHAT_ID = "-1002350825182";
    private final String CHANNEL_CHAT_NANE = "Выгодные объявления с Kufar";

    private final ClothesBrandsRepository clothesBrandsRepository;
    private final CategoryPriceListRepository categoryPriceListRepository;

    public TestChannel(PhotoMessenger<SendPhoto> messenger,
                       TelegramPostManager telegramPostManager,
                       ClothesBrandsRepository clothesBrandsRepository,
                       CategoryPriceListRepository categoryPriceListRepository
    ) {
        super(messenger, telegramPostManager);
        this.clothesBrandsRepository = clothesBrandsRepository;
        this.categoryPriceListRepository = categoryPriceListRepository;
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
//                new OnlyDefiniteCategory("8110")
//                        .or(new OnlyDefiniteCategory("8100"))
//                        .or(new OnlyDefiniteCategory("8080"))
//                        .or(new OnlyDefiniteCategory("8020")),
//
//                new OnlyOwnersAds(),
//                new OnlyOriginalGoodsPolicy(),
//                new OnlyBrandClothesPolicy().or(new OnlyBrandWoomanShoesPolicy()),
//                new OnlyDefinedClothingBrandPolicy(clothesBrandsRepository.get()),
//                new MinPriceForNewGoodsPolicy(new BigDecimal(40)),
//                new WomenClothingPricePolicy(categoryPriceListRepository.getCategoryClothesPriceList())
//                        .or(new WomenShoesPricePolicy(categoryPriceListRepository.getCategoryShoesPriceList()))

        );

    }
}
