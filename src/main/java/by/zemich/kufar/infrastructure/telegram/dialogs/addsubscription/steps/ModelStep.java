//package by.zemich.kufar.input.telegram.dialogs.addsubscription.steps;
//
//import by.zemich.kufar.input.telegram.dialogs.api.AbstractDialogState;
//import by.zemich.kufar.input.telegram.dialogs.api.DialogContext;
//import by.zemich.kufar.input.telegram.dialogs.api.DialogState;
//import by.zemich.kufar.model.criterias.Criteria;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//@Component
//public class ModelStep extends AbstractDialogState<Criteria> {
//
//    public ModelStep(@Qualifier("finishStep") DialogState<Criteria> nextState) {
//        super(nextState);
//    }
//
//    @Override
//    public void handleInput(DialogContext<Criteria> context, Update input) {
//        context.setNextState(nextState);
//    }
//}
