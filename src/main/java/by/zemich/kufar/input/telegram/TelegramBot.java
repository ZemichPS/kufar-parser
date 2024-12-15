package by.zemich.kufar.input.telegram;

import by.zemich.kufar.properties.TelegramProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramProperties properties;


    public TelegramBot(TelegramProperties properties) {
        super(properties.getToken());
        this.properties = properties;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return properties.getName();
    }

    public void sendMessage(String chatId, String message) {
        try {
            execute(new SendMessage(chatId, message));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
