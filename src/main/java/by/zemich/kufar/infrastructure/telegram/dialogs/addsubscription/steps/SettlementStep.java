//package by.zemich.kufar.input.telegram.dialogs.addsubscription.steps;
//
//import by.zemich.kufar.input.telegram.dialogs.api.AbstractDialogState;
//import by.zemich.kufar.input.telegram.dialogs.api.DialogContext;
//import by.zemich.kufar.input.telegram.dialogs.api.DialogState;
//import by.zemich.kufar.model.criterias.Criteria;
//import by.zemich.kufar.service.GeoService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.sendText.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//@Component
//public class SettlementStep extends AbstractDialogState<Criteria> {
//
//    private final GeoService geoService;
//
//    public SettlementStep(@Qualifier("manufactureStep") DialogState<Criteria> criteriaDialogState,
//                          GeoService geoService) {
//        super(criteriaDialogState);
//        this.geoService = geoService;
//    }
//
//
//    @Override
//    public void handleInput(DialogContext<Criteria> context, Update input) {
//        context.setNextState(nextState);
//        SendMessage message = SendMessage.builder()
//                .chatId(input.getMessage().getChatId())
//                .text("Выбери производителя")
//                .build();
//        context.sendMessage(message);
//    }
//}
