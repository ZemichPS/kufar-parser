package by.zemich.kufar.input.telegram;

import by.zemich.kufar.properties.TelegramProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramProperties properties;

    @Value("${telegram.token}")
    private String token = "";

    @Value("${telegram.name}")
    private String name;

    public TelegramBot(TelegramProperties properties) {
        super(properties.getToken());
        this.properties = properties;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return name;
    }
}
