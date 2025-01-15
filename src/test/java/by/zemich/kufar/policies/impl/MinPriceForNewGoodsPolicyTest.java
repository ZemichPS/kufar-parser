package by.zemich.kufar.policies.impl;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.MinPriceForNewGoodsPolicy;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MinPriceForNewGoodsPolicyTest {

    private final MinPriceForNewGoodsPolicy policy
            = new MinPriceForNewGoodsPolicy(new BigDecimal(50));

    @ParameterizedTest
    @ValueSource(strings = { "51", "70", "150" })
    void testIsSatisfiedBy_should_return_true(int value) {
        BigDecimal price = new BigDecimal(value);
        Advertisement ad = Advertisement.builder()
                .subject("Айфон")
                .companyAd(false)
                .fullyFunctional(true)
                .priceInByn(price)
                .parameters(new ArrayList<>())
                .build();
        ad.addParameter(Advertisement.Parameter.builder()
                .label("Состояние")
                .identity("condition")
                .value("Новое")
                .build());
        boolean result = policy.isSatisfiedBy(ad);

        assertTrue(result);
    }

    @Test
    void testIsSatisfiedBy_if_ad_price_equal_should_return_true() {
        Advertisement ad = Advertisement.builder()
                .subject("Айфон")
                .companyAd(false)
                .fullyFunctional(true)
                .priceInByn(new BigDecimal(50))
                .parameters(new ArrayList<>())
                .build();
        ad.addParameter(Advertisement.Parameter.builder()
                .label("Состояние")
                .identity("condition")
                .value("Новое")
                .build());
        boolean result = policy.isSatisfiedBy(ad);

        assertTrue(result);
    }


    @Test
    void testIsSatisfiedBy_should_return_false() {
        Advertisement ad = Advertisement.builder()
                .subject("Айфон")
                .companyAd(false)
                .fullyFunctional(true)
                .priceInByn(new BigDecimal(40))
                .parameters(new ArrayList<>())
                .build();
        ad.addParameter(Advertisement.Parameter.builder()
                .label("Состояние")
                .identity("condition")
                .value("Новое")
                .build());
        boolean result = policy.isSatisfiedBy(ad);

        assertFalse(result);
    }

}