package by.zemich.kufar.input.telegram.dialogs.addsubscription;

import by.zemich.kufar.input.telegram.TelegramBot;
import by.zemich.kufar.input.telegram.dialogs.api.DialogContext;
import by.zemich.kufar.input.telegram.sessions.DialogSession;
import by.zemich.kufar.input.telegram.dialogs.api.DialogState;
import by.zemich.kufar.model.criterias.Criteria;
import by.zemich.kufar.service.api.TextMessenger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AdCriteriaContext implements DialogContext<Criteria> {
    private final DialogSession<Criteria> criteriaDialogSession;
    private DialogState<Criteria> state;
    private final TextMessenger<SendMessage> messenger;


    public AdCriteriaContext(TelegramBot telegramBot,
                             DialogSession<Criteria> criteriaDialogSession,
                             TextMessenger<SendMessage> messenger) {
        this.criteriaDialogSession = criteriaDialogSession;
        this.messenger = messenger;
    }

    @Override
    public DialogSession<Criteria> getSession() {
        return criteriaDialogSession;
    }

    @Override
    public void setNextState(DialogState<Criteria> state) {
        this.state = state;
    }

    @Override
    public void sendMessage(SendMessage message) {
        messenger.sendText(message);
    }

    @Override
    public void handleInput(Update update) {
        this.state.handleInput(this, update);
    }


}
