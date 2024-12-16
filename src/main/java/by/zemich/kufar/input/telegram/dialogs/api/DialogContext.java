package by.zemich.kufar.input.telegram.dialogs.api;

import by.zemich.kufar.input.telegram.sessions.DialogSession;

public interface DialogContext<T, M> {
    DialogSession<T> getSession();

    void setState(DialogState<T> state);

    void sendMessage(M message);

    public void handleInput(String input);

}
