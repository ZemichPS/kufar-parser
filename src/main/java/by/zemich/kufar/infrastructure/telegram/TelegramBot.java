package by.zemich.kufar.infrastructure.telegram;

import by.zemich.kufar.infrastructure.properties.TelegramProperties;
import by.zemich.kufar.application.service.api.PhotoMessenger;
import by.zemich.kufar.application.service.api.TextMessenger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot implements TextMessenger<SendMessage>, PhotoMessenger<SendPhoto> {

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

    @Override
    public void sendText(SendMessage message) {
        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to sendText message to chatId {}, cause:", message.getChatId(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendPhoto(SendPhoto message) {
        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to sendText message to chatId {}, cause:", message.getChatId(), e);
        }
    }
}
