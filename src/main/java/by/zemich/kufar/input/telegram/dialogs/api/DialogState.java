package by.zemich.kufar.input.telegram.dialogs.api;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface DialogState<T> {
    void handleInput(DialogContext<T> context, Update input);

}
