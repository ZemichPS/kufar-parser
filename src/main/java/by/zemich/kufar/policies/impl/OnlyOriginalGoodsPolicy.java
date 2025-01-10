package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

import java.util.regex.Pattern;

public class OnlyOriginalGoodsPolicy implements Policy<Advertisement> {

    // Паттерн для поиска копий или реплик
    private static final Pattern DEFECT_PATTERN = Pattern.compile(
            "(?i)(продам|продаётся)?\\s*(полная|точная)?\\s*(копи[ияю]|реплик[уа]|паль|под(д)?елка)"
    );

    private static final Pattern ORIGINAL_PATTERN = Pattern.compile(
            "(?i)(оригинал|не реплика)"
    );

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        if (isOriginal(advertisement.getDetails())) return true;
        return !containsDataAboutUnoriginality(advertisement.getDetails());
    }

    private boolean containsDataAboutUnoriginality(String adDetails) {
        if (adDetails == null || adDetails.trim().isEmpty()) {
            return false;
        }
        return DEFECT_PATTERN.matcher(adDetails.toLowerCase()).find();
    }

    private boolean isOriginal(String adDetails) {
        if (adDetails == null || adDetails.trim().isEmpty()) {
            return false;
        }
        return ORIGINAL_PATTERN.matcher(adDetails.toLowerCase()).find();
    }
}
