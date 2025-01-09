package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

import java.util.List;
import java.util.Objects;

public class ExcludedWomenShoesBrandPolicy implements Policy<Advertisement> {
    private final List<String> excludedBrands;

    public ExcludedWomenShoesBrandPolicy(List<String> excludedBrands) {
        this.excludedBrands = excludedBrands;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        if (!isApplicable(advertisement)) return false;

        return advertisement.getParameterByIdentity("women_shoes_brand")
                .map(param -> excludedBrands.stream()
                        .map(String::toLowerCase)
                        .noneMatch(b -> b.equals(param.getValue().toLowerCase())))
                .orElse(false);

    }

    private boolean isApplicable(Advertisement advertisement) {
        if (Objects.isNull(advertisement)) return false;
        return advertisement.getParameterValueByParameterName("women_shoes_brand")
                .map(param -> !param.isEmpty() && !param.isBlank())
                .orElse(false);
    }

}
