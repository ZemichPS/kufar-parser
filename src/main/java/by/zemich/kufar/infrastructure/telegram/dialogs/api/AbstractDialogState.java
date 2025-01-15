package by.zemich.kufar.infrastructure.telegram.dialogs.api;

public abstract class AbstractDialogState<T> implements DialogState<T> {
    protected final DialogState<T> nextState;

    protected AbstractDialogState(DialogState<T> nextState) {
        this.nextState = nextState;
    }


}
