//package by.zemich.kufar.input.telegram.dialogs.addsubscription.steps;
//
//import by.zemich.kufar.input.telegram.dialogs.api.AbstractDialogState;
//import by.zemich.kufar.input.telegram.dialogs.api.DialogContext;
//import by.zemich.kufar.input.telegram.dialogs.api.DialogState;
//import by.zemich.kufar.model.criterias.Criteria;
//import by.zemich.kufar.service.ManufactureService;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.sendText.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
//
//import java.util.List;
//
//@Component
//public class ManufactureStep extends AbstractDialogState<Criteria> {
//
//    private final ManufactureService manufactureService;
//
//    protected ManufactureStep(
//            @Qualifier("modelStep")
//            DialogState<Criteria> nextState,
//                              ManufactureService manufactureService) {
//        super(nextState);
//        this.manufactureService = manufactureService;
//    }
//
//    @Override
//    public void handleInput(DialogContext<Criteria> context, Update input) {
//        Long chatId = input.getMessage().getChatId();
//        context.setNextState(nextState);
//        SendMessage message = getMessage(chatId);
//        context.sendMessage(message);
//    }
//
//    private SendMessage getMessage(Long chatId) {
//        return SendMessage.builder()
//                .text("Выберите или введите производителя")
//                .chatId(chatId)
//                .replyMarkup(getReplyKeyboardMarkup())
//                .build();
//    }
//
//    private ReplyKeyboardMarkup getReplyKeyboardMarkup() {
//        List<KeyboardRow> buttons = manufactureService.getAllManufacturers().stream()
//                .map(data -> KeyboardButton.builder()
//                        .text(data.getName())
//                        .build())
//                .map(button -> new KeyboardRow(List.of(button)))
//                .toList();
//
//        return ReplyKeyboardMarkup.builder()
//                .keyboard(buttons)
//                .resizeKeyboard(true)
//                .oneTimeKeyboard(true)
//                .build();
//    }
//}
