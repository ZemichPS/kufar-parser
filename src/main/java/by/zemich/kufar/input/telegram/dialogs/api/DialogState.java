package by.zemich.kufar.input.telegram.dialogs.api;

public interface DialogState<T, M, I> {
    void handleInput(DialogContext<T, M> context, I input);
}
