//package by.zemich.kufar.input.telegram.dialogs.addsubscription.steps;
//
//import by.zemich.kufar.input.telegram.dialogs.api.AbstractDialogState;
//import by.zemich.kufar.input.telegram.dialogs.api.DialogContext;
//import by.zemich.kufar.input.telegram.dialogs.api.DialogState;
//import by.zemich.kufar.model.criterias.Criteria;
//import by.zemich.kufar.service.GeoService;
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
//public class RegionStep extends AbstractDialogState<Criteria> {
//    private final GeoService geoService;
//
//    public RegionStep(GeoService geoService,
//                      @Qualifier("settlementStep")
//                      DialogState<Criteria> nextDialogState) {
//        super(nextDialogState);
//        this.geoService = geoService;
//    }
//
//    @Override
//    public void handleInput(DialogContext<Criteria> context, Update update) {
//        Long chatId = update.getMessage().getChatId();
//        context.setNextState(nextState);
//        context.sendMessage(getMessage(chatId));
//    }
//
//    private SendMessage getMessage(Long chatId) {
//        return SendMessage.builder()
//                .text("Выберите область")
//                .chatId(chatId)
//                .replyMarkup(getReplyKeyboardMarkup())
//                .build();
//    }
//
//    private ReplyKeyboardMarkup getReplyKeyboardMarkup() {
//        List<KeyboardRow> buttons = geoService.findAllRegions().stream()
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
