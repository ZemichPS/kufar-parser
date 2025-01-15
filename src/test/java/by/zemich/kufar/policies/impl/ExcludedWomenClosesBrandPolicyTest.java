package by.zemich.kufar.policies.impl;


import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.ExcludedWomenClosesBrandPolicy;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ExcludedWomenClosesBrandPolicyTest {

    private final ExcludedWomenClosesBrandPolicy policy = new ExcludedWomenClosesBrandPolicy(
            List.of("ТВОЕ", "Reebok")
    );


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
    public void testSatisfiedBy_if_blank_should_return_false() {
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
                .value("       ")
                .build());
        boolean result = policy.isSatisfiedBy(ad);
        assertFalse(result);
    }

    @Test
    public void testSatisfiedBy_ifExcludeBrand_shouldReturnFalse() {
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
                .value("тВое")
                .build());

        ad.addParameter(Advertisement.Parameter.builder()
                .label("Бренд")
                .identity("women_clothes_brand")
                .value("reebok")
                .build());
        boolean result = policy.isSatisfiedBy(ad);
        assertFalse(result);
    }
}