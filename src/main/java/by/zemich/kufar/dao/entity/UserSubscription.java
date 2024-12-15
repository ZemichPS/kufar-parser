package by.zemich.kufar.dao.entity;


import by.zemich.kufar.model.criterias.Criteria;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserSubscription {
    private UUID id;
    private User subscriber;

    @Setter(AccessLevel.NONE)
    private List<Criteria> criteriaList = new ArrayList<>();

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
