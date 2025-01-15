package by.zemich.kufar.policies.impl;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.FastSalesPolicy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FastSalesPolicyTest {

    private FastSalesPolicy policy = new FastSalesPolicy();

    @ParameterizedTest(name = "value is -> {0}")
    @ValueSource(strings = {
            "срочно продаётся",
            "срочна продаётся",
            "срочно!",
            "срочно!!!!",
            "срочно !",
            "продам срочна",
            "срочная продажа"
    })
    void isSatisfiedBy(String details) {
        Advertisement advertisement = getAdvertisement();
        advertisement.setDetails(details);
        assertTrue(policy.isSatisfiedBy(advertisement));
    }

    private static Advertisement getAdvertisement() {
        return Advertisement.builder()
                .subject("Some device")
                .companyAd(false)
                .fullyFunctional(true)
                .parameters(new ArrayList<>())
                .build();
    }
}