package by.zemich.kufar.application.service.channels;

import by.zemich.kufar.application.service.channels.api.TelegramChannel;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.*;
import by.zemich.kufar.application.service.PostManager;
import by.zemich.kufar.application.service.api.PhotoMessenger;
import by.zemich.kufar.application.service.channels.api.Channel;
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

import static java.lang.Thread.sleep;

@Component
@Profile("prod")
public class BrandedWomenClothesChannel extends TelegramChannel {

    private final String CHANNEL_CHAT_ID = "-1002270323996";
    private final String CHANNEL_CHAT_NANE = "Брендовая женская одежда";

    public BrandedWomenClothesChannel(PhotoMessenger<SendPhoto> messenger,
                                      PostManager postManager
    ) {
        super(messenger, postManager);
        this.policies.addAll(
                List.of(
                        new OnlyDefiniteCategory("8110")
                                .or(new OnlyDefiniteCategory("8100"))
                                .or(new OnlyDefiniteCategory("8080"))
                                .or(new OnlyDefiniteCategory("8020")),

                        new OnlyOwnersAds(),
                        new OnlyOriginalGoodsPolicy(),
                        new OnlyBrandClothesPolicy().or(new OnlyBrandWoomanShoesPolicy()),
                        new OnlyDefinedClothingBrandPolicy(getBrands()),
                        new MinPriceForNewGoodsPolicy(new BigDecimal(40)),
                        new WomenClothingPricePolicy(getCategoryClothesPriceList())
                                .or(new WomenShoesPricePolicy(getCategoryShoesPriceList()))

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

    private Map<String, BigDecimal> getCategoryShoesPriceList() {
        try {
            Path pathToFile = Path.of(ClassLoader.getSystemResource("shoes_type_price_list.txt")
                    .toURI());

            return Files.readAllLines(pathToFile).stream()
                    .map(String::toLowerCase)
                    .map(line -> line.split("-"))
                    .collect(Collectors.toMap(
                            array -> array[0].trim(),
                            array -> new BigDecimal(array[1].trim())
                    ));

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, BigDecimal> getCategoryClothesPriceList() {
        try {
            Path pathToFile = Path.of(ClassLoader.getSystemResource("clothing_type_price_list.txt")
                    .toURI());

            return Files.readAllLines(pathToFile).stream()
                    .map(String::toLowerCase)
                    .map(line -> line.split("-"))
                    .collect(Collectors.toMap(
                            array -> array[0].trim().toLowerCase(),
                            array -> new BigDecimal(array[1].trim())
                    ));

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getBrands() {
        try {
            Path path = Path.of(ClassLoader.getSystemResource("clothes_brands").toURI());
            return Files.readAllLines(path)
                    .stream()
                    .map(String::toLowerCase)
                    .toList();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
