package by.zemich.kufar.infrastructure.repository.redisrepository;

import by.zemich.kufar.infrastructure.telegram.dialogs.addsubscription.SubscriptionDialogSession;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SessionRepository extends CrudRepository<SubscriptionDialogSession, UUID> {
}
