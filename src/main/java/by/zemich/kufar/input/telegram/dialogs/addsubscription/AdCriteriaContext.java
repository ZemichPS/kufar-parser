package by.zemich.kufar.input.telegram.dialogs.addsubscription;

import by.zemich.kufar.dao.entity.UserSubscription;
import by.zemich.kufar.input.telegram.TelegramBot;
import by.zemich.kufar.input.telegram.dialogs.api.DialogContext;
import by.zemich.kufar.input.telegram.sessions.DialogSession;
import by.zemich.kufar.input.telegram.dialogs.api.DialogState;
import by.zemich.kufar.model.criterias.Criteria;
import by.zemich.kufar.service.api.Messenger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.UUID;

public class AdCriteriaContext implements DialogContext<Criteria> {
    private final DialogSession<Criteria> criteriaDialogSession;
    private DialogState<Criteria> state;
    private final Messenger<SendMessage> messenger;


    public AdCriteriaContext(TelegramBot telegramBot,
                             DialogSession<Criteria> criteriaDialogSession,
                             Messenger<SendMessage> messenger) {
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
        messenger.send(message);
    }

    @Override
    public void handleInput(Update update) {
        this.state.handleInput(this, update);
    }


}
