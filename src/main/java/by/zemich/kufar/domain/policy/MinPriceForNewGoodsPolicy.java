package by.zemich.kufar.domain.policy;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.api.Policy;

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
