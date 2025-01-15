package by.zemich.kufar.policies.impl;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.OnlyBrandClothesPolicy;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OnlyBrandClothesPolicyTest {

    private final OnlyBrandClothesPolicy policy = new OnlyBrandClothesPolicy();

    @Test
    public void testSatisfiedBy_if_relevant_should_return_true() {
        Advertisement ad = Advertisement.builder()
                .subject("Спортивки adidas")
                .companyAd(false)
                .fullyFunctional(true)
                .priceInByn(new BigDecimal(70))
                .parameters(new ArrayList<>())
                .build();

        ad.addParameter(Advertisement.Parameter.builder()
                .label("Бренд")
                .identity("women_clothes_brand")
                .value("Adidas")
                .build());
        boolean result = policy.isSatisfiedBy(ad);
        assertTrue(result);
    }

    @Test
    public void testSatisfiedBy_ifBrandIsEmpty_should_return_false() {
        Advertisement ad = Advertisement.builder()
                .subject("Спортивки adidas")
                .companyAd(false)
                .fullyFunctional(true)
                .priceInByn(new BigDecimal(70))
                .parameters(new ArrayList<>())
                .build();

        ad.addParameter(Advertisement.Parameter.builder()
                .label("Бренд")
                .identity("women_clothes_brand")
                .value("")
                .build());
        boolean result = policy.isSatisfiedBy(ad);
        assertFalse(result);
    }


    @Test
    public void testSatisfiedBy_ifBrandIsAnother_should_return_false() {
        Advertisement ad = Advertisement.builder()
                .subject("Спортивки adidas")
                .companyAd(false)
                .fullyFunctional(true)
                .priceInByn(new BigDecimal(70))
                .parameters(new ArrayList<>())
                .build();

        ad.addParameter(Advertisement.Parameter.builder()
                .label("Бренд")
                .identity("women_clothes_brand")
                .value("Другой")
                .build());
        boolean result = policy.isSatisfiedBy(ad);
        assertFalse(result);
    }

}