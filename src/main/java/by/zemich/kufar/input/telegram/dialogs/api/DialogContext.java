package by.zemich.kufar.input.telegram.dialogs.api;

import by.zemich.kufar.input.telegram.sessions.DialogSession;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface DialogContext<T> {
    DialogSession<T> getSession();

    void setNextState(DialogState<T> state);

    void sendMessage(SendMessage message);

    public void handleInput(Update update);


}
