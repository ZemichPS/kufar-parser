package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ExcludedWomenClosesBrandPolicy implements Policy<Advertisement> {
    private final List<String> excludedBrands;

    public ExcludedWomenClosesBrandPolicy(List<String> excludedBrands) {
        this.excludedBrands = excludedBrands;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        if (!isApplicable(advertisement)) return false;

        return advertisement.getParameterByIdentity("women_clothes_brand")
                .map(param -> excludedBrands.stream()
                        .map(String::toLowerCase)
                        .noneMatch(b -> b.equals(param.getValue().toLowerCase())))
                .orElse(false);

    }


    private boolean isApplicable(Advertisement advertisement) {
        if (Objects.isNull(advertisement)) return false;
        return advertisement.getParameterValueByParameterName("women_clothes_brand")
                .map(param -> !param.isEmpty() && !param.isBlank())
                .orElse(false);
    }
}
