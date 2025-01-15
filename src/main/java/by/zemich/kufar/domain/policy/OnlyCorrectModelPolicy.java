package by.zemich.kufar.domain.policy;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.api.Policy;

public class OnlyCorrectModelPolicy implements Policy<Advertisement> {

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return advertisement.getModel()
                .filter("другая"::equalsIgnoreCase)
                .isEmpty();

    }
}
