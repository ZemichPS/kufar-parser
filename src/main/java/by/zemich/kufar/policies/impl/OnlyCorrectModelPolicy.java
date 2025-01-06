package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

public class OnlyCorrectModelPolicy implements Policy<Advertisement> {

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        if(advertisement.getModel().isPresent()){
            return !advertisement.getModel().get().equalsIgnoreCase("другая");
        }

        return false;
    }
}
