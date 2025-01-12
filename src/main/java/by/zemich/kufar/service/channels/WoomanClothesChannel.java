package by.zemich.kufar.service.channels;

import by.zemich.kufar.policies.impl.IsChildCategoryPolicy;
import by.zemich.kufar.policies.impl.OnlyOwnersAds;
import by.zemich.kufar.policies.impl.WomenClothingPricePolicy;
import by.zemich.kufar.policies.impl.WomenShoesPricePolicy;
import by.zemich.kufar.service.PostManager;
import by.zemich.kufar.service.api.Channel;
import by.zemich.kufar.service.api.PhotoMessenger;
import by.zemich.kufar.service.SubCategoryService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Component
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
                        new OnlyOwnersAds(),
                        new WomenClothingPricePolicy(getCategoryClothesPriceList())
                                .or(new WomenShoesPricePolicy(getCategoryShoesPriceList()))
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

    private Map<String, BigDecimal> getCategoryClothesPriceList() {
        try {
            return Files.readAllLines(Path.of("clothing_type_price_list.txt")).stream()
                    .map(line -> line.replaceAll("\\s+", ""))
                    .map(line -> line.split("-"))
                    .collect(Collectors.toMap(
                            array -> array[0],
                            array -> new BigDecimal(array[1])
                    ));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, BigDecimal> getCategoryShoesPriceList() {
        try {
            return Files.readAllLines(Path.of("shoes_type_price_list.txt")).stream()
                    .map(line -> line.replaceAll("\\s+", ""))
                    .map(line -> line.split("-"))
                    .collect(Collectors.toMap(
                            array -> array[0],
                            array -> new BigDecimal(array[1])
                    ));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
