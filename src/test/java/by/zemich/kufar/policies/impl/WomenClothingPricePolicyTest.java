package by.zemich.kufar.policies.impl;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.WomenClothingPricePolicy;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class WomenClothingPricePolicyTest {

    private final WomenClothingPricePolicy policy = new WomenClothingPricePolicy(
            Map.of(
                    "верхняя одежда", new BigDecimal(100),
                    "свитеры и толстовки", new BigDecimal(60),
                    "джинсы, брюки", new BigDecimal(50),
                    "костюмы, пиджаки и жакеты", new BigDecimal(60)
            )
    );


    @Test
    void isSatisfiedBy_WhenInappropriatePricesHigherAccordingToCategory_ShouldReturnFalse() {
        Advertisement advertisement = getAdvertisement();
        advertisement.addParameter(Advertisement.Parameter.builder()
                .label("Вид одежды")
                .identity("women_clothes_type")
                .value("Костюмы, пиджаки и жакеты")
                .build()
        ).setPriceInByn(new BigDecimal(61));
        assertFalse(policy.isSatisfiedBy(advertisement));
    }

    @ParameterizedTest
    @MethodSource("provideAppropriateAdvertisements")
    void isSatisfiedBy_WhenAppropriatePricesLessOrEqualAccordingToCategory_ShouldReturnTrue(Advertisement advertisement) {
        assertTrue(policy.isSatisfiedBy(advertisement));
    }


    private static Stream<Arguments> provideAppropriateAdvertisements() {
        return Stream.of(
                Arguments.of(getAdvertisement().addParameter(Advertisement.Parameter.builder()
                        .label("Вид одежды")
                        .identity("women_clothes_type")
                        .value("Джинсы, брюки")
                        .build()
                ).setPriceInByn(new BigDecimal(50))),

                Arguments.of(getAdvertisement().addParameter(Advertisement.Parameter.builder()
                        .label("Вид одежды")
                        .identity("women_clothes_type")
                        .value("Верхняя одежда")
                        .build()
                ).setPriceInByn(new BigDecimal(80))),

                Arguments.of(getAdvertisement().addParameter(Advertisement.Parameter.builder()
                        .label("Вид одежды")
                        .identity("women_clothes_type")
                        .value("Костюмы, пиджаки и жакеты")
                        .build()
                ).setPriceInByn(new BigDecimal(40)))
        );
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