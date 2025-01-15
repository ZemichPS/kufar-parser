package by.zemich.kufar.domain.policy;

import by.zemich.kufar.domain.policy.api.Policy;

public class MinimumRequredAmountOfDataForMarketPriceCountingPolicy implements Policy<Integer> {
    private final Integer NEEDED_ARRAY_SIZE = 10;

    public MinimumRequredAmountOfDataForMarketPriceCountingPolicy() {
    }

    @Override
    public boolean isSatisfiedBy(Integer currentSize) {
        return currentSize >= NEEDED_ARRAY_SIZE;
    }
}
