package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

public class AreaPolicy implements Policy<Advertisement> {

    private final String area;

    public AreaPolicy(String area) {
        this.area = area;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return advertisement.getParameters().stream()
                .anyMatch(param -> "area".equals(area));
    }
}
