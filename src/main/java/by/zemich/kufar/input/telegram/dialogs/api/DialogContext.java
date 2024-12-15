package by.zemich.kufar.input.telegram.dialogs.api;

public interface DialogContext<T> {
    DialogSession<T> getSession();

    void setState(DialogState<T> state);

    void sendMessage(String message);

    public void handleInput(String input);

}
