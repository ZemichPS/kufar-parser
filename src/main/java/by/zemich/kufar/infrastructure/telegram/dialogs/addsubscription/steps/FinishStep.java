//package by.zemich.kufar.input.telegram.dialogs.addsubscription.steps;
//
//import by.zemich.kufar.domain.model.UserSubscription;
//import by.zemich.kufar.input.telegram.dialogs.addsubscription.SubscriptionDialogSession;
//import by.zemich.kufar.input.telegram.dialogs.api.AbstractDialogState;
//import by.zemich.kufar.input.telegram.dialogs.api.DialogContext;
//import by.zemich.kufar.input.telegram.dialogs.api.DialogState;
//import by.zemich.kufar.model.criterias.Criteria;
//import by.zemich.kufar.service.SubscriptionManager;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.sendText.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//import java.util.UUID;
//
//@Component
//public class FinishStep extends AbstractDialogState<Criteria> {
//    private final SubscriptionManager subscriptionManager;
//
//    protected FinishStep(@Qualifier("regionStep") DialogState<Criteria> nextState, SubscriptionManager subscriptionManager) {
//        super(nextState);
//        this.subscriptionManager = subscriptionManager;
//    }
//
//    @Override
//    public void handleInput(DialogContext<Criteria> context, Update input) {
//        SubscriptionDialogSession subscriptionSession = (SubscriptionDialogSession) context.getSession();
//        UserSubscription subscription =  subscriptionSession.createUserSubscription();
//        subscriptionManager.subscribe(subscription);
//        context.sendMessage(getMessage(subscription));
//    }
//
//    private SendMessage getMessage(UserSubscription subscription) {
//        return SendMessage.builder()
//                .text("Выполнено!")
//                .text(subscription.toString())
//                .build();
//    }
//}
