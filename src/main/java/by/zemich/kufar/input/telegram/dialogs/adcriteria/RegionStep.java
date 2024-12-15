package by.zemich.kufar.input.telegram.dialogs.adcriteria;

import by.zemich.kufar.input.telegram.dialogs.api.DialogContext;
import by.zemich.kufar.input.telegram.dialogs.api.DialogState;
import by.zemich.kufar.model.criterias.Criteria;
import by.zemich.kufar.model.criterias.LocationCriteria;

import java.math.BigDecimal;

public class RegionStep implements DialogState<Criteria> {

    @Override
    public void handleInput(DialogContext<Criteria> context, String input) {
        LocationCriteria.Location location = new LocationCriteria.Location();
        location.setRegion(input);
        LocationCriteria criteria = new LocationCriteria(location);
        context.getSession().addStepValue("location", criteria);
        context.setState(this);
        context.sendMessage("Выберите город");

    }
}
