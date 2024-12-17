package by.zemich.kufar.input.telegram.dialogs.addsubscription;

import by.zemich.kufar.dao.entity.UserSubscription;
import by.zemich.kufar.input.telegram.sessions.AbstractDialogSession;
import by.zemich.kufar.model.criterias.Criteria;

import java.util.UUID;

public class SubscriptionSession extends AbstractDialogSession<Criteria> {

    public SubscriptionSession(UUID userID) {
        super(userID);
    }

    public UserSubscription createUserSubscription() {
        UserSubscription userSubscription = new UserSubscription(UUID.randomUUID(), getUserId());
        criteriaMap.forEach((key, value) -> {
            userSubscription.addCriteria(value);
        });
        return userSubscription;
    }
}
