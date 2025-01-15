package by.zemich.kufar.domain.model.criterias;

import by.zemich.kufar.domain.model.Advertisement;

public interface Criteria {
    boolean isSatisfied(Advertisement advertisement);
}
