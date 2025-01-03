package by.zemich.kufar.dao.redisrepository;

import by.zemich.kufar.input.telegram.dialogs.addsubscription.SubscriptionDialogSession;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SessionRepository extends CrudRepository<SubscriptionDialogSession, UUID> {
}
