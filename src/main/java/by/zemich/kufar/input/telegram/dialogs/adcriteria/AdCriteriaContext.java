package by.zemich.kufar.input.telegram.dialogs.adcriteria;

import by.zemich.kufar.input.telegram.TelegramBot;
import by.zemich.kufar.input.telegram.dialogs.api.DialogContext;
import by.zemich.kufar.input.telegram.dialogs.api.DialogSession;
import by.zemich.kufar.input.telegram.dialogs.api.DialogState;
import by.zemich.kufar.model.criterias.Criteria;

public class AdCriteriaContext implements DialogContext<Criteria> {
    private final TelegramBot telegramBot;
    private final DialogSession<Criteria> criteriaDialogSession;

    private DialogState<Criteria> state;

    public AdCriteriaContext(TelegramBot telegramBot,
                             DialogSession<Criteria> criteriaDialogSession) {
        this.telegramBot = telegramBot;
        this.criteriaDialogSession = criteriaDialogSession;
    }

    @Override
    public DialogSession<Criteria> getSession() {
        return null;
    }

    @Override
    public void setState(DialogState<Criteria> state) {

    }

    @Override
    public void sendMessage(String message) {
        telegramBot.sendMessage("sdfsdfsdf", message);
    }

    @Override
    public void handleInput(String input) {
        this.state.handleInput(this, input);
    }
}
