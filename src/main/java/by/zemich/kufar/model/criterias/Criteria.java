package by.zemich.kufar.model.criterias;

import by.zemich.kufar.dao.entity.Advertisement;

public interface Criteria {
    boolean isSatisfied(Advertisement advertisement);
}
