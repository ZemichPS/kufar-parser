package by.zemich.kufar.input.telegram.dialogs.api;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public abstract class AbstractDialogState<T> implements DialogState<T> {
    protected final DialogState<T> nextState;

    protected AbstractDialogState(DialogState<T> nextState) {
        this.nextState = nextState;
    }


}
