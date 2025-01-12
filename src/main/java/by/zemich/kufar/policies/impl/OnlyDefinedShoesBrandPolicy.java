package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

import java.util.List;
import java.util.Objects;

public class OnlyDefinedShoesBrandPolicy implements Policy<Advertisement> {

    private final List<String> approvedBrands;

    public OnlyDefinedShoesBrandPolicy(List<String> approvedBrands) {
        this.approvedBrands = approvedBrands;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        if(!isApplicable(advertisement)) return false;

        return approvedBrands.stream()
                .map(String::toLowerCase)
                .anyMatch(approvedBrand -> approvedBrand.equalsIgnoreCase(advertisement.getParameterValueByParameterName("women_clothes_brand").orElse("").toLowerCase()));
    }

    private boolean isApplicable(Advertisement advertisement) {
        if (Objects.isNull(advertisement)) return false;
        return advertisement.getParameterValueByParameterName("women_shoes_brand")
                .map(param -> !param.isEmpty() && !param.isBlank())
                .orElse(false);
    }
}
