package by.zemich.kufar.input.telegram.dialogs.api;

public interface DialogState<T> {
    void handleInput(DialogContext<T> context, String input);
}
