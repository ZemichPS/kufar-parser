package by.zemich.kufar.input.telegram.dialogs.addsubscription;

import by.zemich.kufar.dao.entity.UserSubscription;
import by.zemich.kufar.input.telegram.sessions.AbstractDialogSession;
import by.zemich.kufar.model.criterias.Criteria;

import java.util.UUID;

public class SubscriptionSession extends AbstractDialogSession<Criteria> {

    UserSubscription createUserSubscription(UUID subscriberId) {
        UserSubscription userSubscription = new UserSubscription(UUID.randomUUID(), subscriberId);
        criteriaMap.forEach((key, value) -> {
            userSubscription.addCriteria(value);
        });
        return userSubscription;
    }
}
