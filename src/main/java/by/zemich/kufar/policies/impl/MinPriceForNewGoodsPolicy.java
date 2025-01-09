package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

import java.math.BigDecimal;

public class MinPriceForNewGoodsPolicy implements Policy<Advertisement> {

    private final BigDecimal minPrice;

    public MinPriceForNewGoodsPolicy(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return !advertisement.getCondition().equalsIgnoreCase("новое") ||
                advertisement.getPriceInByn().compareTo(minPrice) >= 0;
    }
}
