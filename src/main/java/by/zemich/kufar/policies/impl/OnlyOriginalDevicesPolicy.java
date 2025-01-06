package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

import java.util.regex.Pattern;

public class OnlyOriginalDevicesPolicy implements Policy<Advertisement> {

    private final Pattern DEFECT_PATTERN = Pattern.compile(
            "(?i)(?x)" + // Игнорировать регистр и разрешить комментарии
                    "(" +
                    // копия
                    "(продам|продаётся\\s+)?(полная|полную|точная|точную\\s+)?копи[ияю]" +
                    ")"
    );


    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return containsDataAboutUnoriginality(advertisement.getDetails());
    }

    private boolean containsDataAboutUnoriginality(String adDetails) {
        if(adDetails == null || adDetails.isEmpty()) return false;
        return DEFECT_PATTERN.matcher(adDetails.toLowerCase()).find();
    }


}
