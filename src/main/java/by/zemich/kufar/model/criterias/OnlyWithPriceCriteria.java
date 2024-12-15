package by.zemich.kufar.model.criterias;

import by.zemich.kufar.dao.entity.Advertisement;

import java.math.BigDecimal;

public class OnlyWithPriceCriteria implements Criteria {
    @Override
    public boolean isSatisfied(Advertisement advertisement) {
        return advertisement.getPriceInByn().compareTo(BigDecimal.ZERO) > 0;
    }
}
