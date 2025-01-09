package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

import java.util.Objects;
import java.util.Optional;

public class AreaPolicy implements Policy<Advertisement> {

    private final String targetArea;

    public AreaPolicy(String targetArea) {
        this.targetArea = Objects.requireNonNull(targetArea, "Target area must not be null");
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        if(!isApplicable(advertisement)) return false;

        Optional<Advertisement.Parameter> areaParameter = advertisement.getParameterByIdentity("area");
        return areaParameter.map(param-> param.getValue()
                .equalsIgnoreCase(targetArea))
                .orElse(false);
    }

    private boolean isApplicable(Advertisement advertisement) {
        return advertisement.getParameterByIdentity("area").isPresent();
    }
}
