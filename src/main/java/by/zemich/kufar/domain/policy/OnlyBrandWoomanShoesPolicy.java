package by.zemich.kufar.domain.policy;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.api.Policy;

public class OnlyBrandWoomanShoesPolicy implements Policy<Advertisement> {
    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return "8100".equalsIgnoreCase(advertisement.getCategory())
                && advertisement.getParameterValueByParameterName("women_shoes_brand").isPresent();
    }
}
