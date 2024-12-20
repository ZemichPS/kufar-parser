package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

public class OnlyOriginalDevicesPolicy implements Policy<Advertisement> {
    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return containsDataAboutUnoriginality(advertisement.getDetails());
    }

    private boolean containsDataAboutUnoriginality(String adDetails) {
        return adDetails.toLowerCase().contains("копия");
    }


}
