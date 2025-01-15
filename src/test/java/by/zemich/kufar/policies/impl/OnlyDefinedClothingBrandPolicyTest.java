package by.zemich.kufar.policies.impl;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.OnlyDefinedClothingBrandPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

class OnlyDefinedClothingBrandPolicyTest {

    private final OnlyDefinedClothingBrandPolicy policy = new OnlyDefinedClothingBrandPolicy(
            List.of(
                    "armani",
                    "adidas",
                    "Nike",
                    "Boss",
                    "Asics"
            )
    );

    @ParameterizedTest(name = "value is -> {0}")
    @ValueSource(strings = {"Armani", "nike", "Adidas"})
    void isSatisfiedBy_WhenAdvertisementBrandIsAppropriate_thenReturnsTrue(String brand) {
        Advertisement advertisement = getAdvertisement();
        advertisement.addParameter(Advertisement.Parameter.builder()
                .label("Бренд")
                .identity("women_clothes_brand")
                .value(brand)
                .build());
        Assertions.assertTrue(policy.isSatisfiedBy(advertisement));
    }

    @Test
    void isSatisfiedBy_WhenAdvertisementBrandIsInappropriate_thenReturnsFalse() {
        Advertisement advertisement = getAdvertisement();
        advertisement.addParameter(Advertisement.Parameter.builder()
                .label("Бренд")
                .identity("women_clothes_brand")
                .value("твоё")
                .build());
        Assertions.assertFalse(policy.isSatisfiedBy(advertisement));
    }

    private static Advertisement getAdvertisement() {
        return Advertisement.builder()
                .subject("Female clothes")
                .companyAd(false)
                .fullyFunctional(true)
                .parameters(new ArrayList<>())
                .build();
    }
}