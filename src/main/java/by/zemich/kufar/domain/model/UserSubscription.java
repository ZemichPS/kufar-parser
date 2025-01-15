package by.zemich.kufar.domain.model;


import by.zemich.kufar.domain.model.criterias.Criteria;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@RedisHash("user_subscription")
public class UserSubscription {
    @Id
    private UUID id;
    private UUID subscriberId;
    @TimeToLive
    private final Long expiresIn;
    private List<Criteria> criteriaList = new ArrayList<>();

    public UserSubscription(UUID id, UUID subscriberId, Long expiresIn) {
        this.id = id;
        this.subscriberId = subscriberId;
        this.expiresIn = expiresIn;
    }

    public UserSubscription(UUID id, UUID subscriberId) {
        this.id = id;
        this.subscriberId = subscriberId;
        this.expiresIn = Duration.ofDays(7).getSeconds();
    }

    public void addCriteria(Criteria criteria) {
        criteriaList.add(criteria);
    }

    public void removeCriteria(Criteria criteria) {
        criteriaList.remove(criteria);
    }

    public boolean isSatisfied(Advertisement advertisement) {
        return criteriaList.stream()
                .allMatch(criteria -> criteria.isSatisfied(advertisement));
    }
}
