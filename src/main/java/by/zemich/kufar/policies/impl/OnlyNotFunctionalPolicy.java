package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

public class OnlyNotFunctionalPolicy implements Policy<Advertisement> {
    @Override
    public boolean isSatisfiedBy(Advertisement domainObject) {
        return false;
    }

    // TODO изменить тело метода
    private boolean containsDataAboutNotFunctional(String adDetails) {
        return adDetails.toLowerCase().contains("не исправен");
    }
}
