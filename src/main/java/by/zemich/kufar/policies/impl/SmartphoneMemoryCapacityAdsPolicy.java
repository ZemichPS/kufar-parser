package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.policies.api.Policy;

public class SmartphoneMemoryCapacityAdsPolicy implements Policy<Advertisement> {

    private final String memoryCapacity;

    public SmartphoneMemoryCapacityAdsPolicy(String memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    @Override
    public boolean isSatisfiedBy(Advertisement advertisement) {
        return advertisement.getParameterValueByParameterName("phablet_phones_memory")
                .map(value -> value.split(" ")[0])
                .orElse("").equals(memoryCapacity);
    }
}
