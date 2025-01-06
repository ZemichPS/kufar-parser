package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

public class AreaPolicy implements Policy<Advertisement> {

    private final String purposeArea;

    public AreaPolicy(String purposeArea) {
        this.purposeArea = purposeArea;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        String area = advertisement.getParameters().stream()
                .filter(param -> "area".equals(param.getIdentity()))
                .map(Advertisement.Parameter::getValue)
                .findFirst()
                .orElse("");

        return area.equalsIgnoreCase(purposeArea);
    }
}
