package by.zemich.kufar.policies.impl;

import by.zemich.kufar.policies.api.Policy;

public class MinimumRequredAmountOfDataForMarketPriceCountingPolicy implements Policy<Integer> {
    private final Integer NEEDED_ARRAY_SIZE = 20;

    public MinimumRequredAmountOfDataForMarketPriceCountingPolicy() {
    }

    @Override
    public boolean isSatisfiedBy(Integer currentSize) {
        return currentSize >= NEEDED_ARRAY_SIZE;
    }
}
