package by.zemich.kufar.input.telegram.dialogs.addsubscription.steps;

import by.zemich.kufar.input.telegram.dialogs.api.DialogContext;
import by.zemich.kufar.input.telegram.dialogs.api.DialogState;
import by.zemich.kufar.model.criterias.Criteria;
import by.zemich.kufar.model.criterias.LocationCriteria;
import by.zemich.kufar.service.GeoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
public class RegionStep implements DialogState<Criteria, SendMessage> {

    private final GeoService geoService;

    @Override
    public void handleInput(DialogContext<Criteria, SendMessage> context, String input) {
        LocationCriteria.Location location = new LocationCriteria.Location();
        location.setRegion(input);
        LocationCriteria criteria = new LocationCriteria(location);
        context.getSession().addStepValue("location", criteria);
        context.setState(this);
        context.sendMessage(new SendMessage());
    }


}
