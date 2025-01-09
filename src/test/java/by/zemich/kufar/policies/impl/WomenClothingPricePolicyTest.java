package by.zemich.kufar.policies.impl;

import by.zemich.kufar.dao.entity.Advertisement;
import jakarta.el.MethodReference;
import org.junit.jupiter.api.DisplayName;
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
                    "свитеры и толстовки", new BigDecimal(50),
                    "джинсы, брюки", new BigDecimal(50)
            )
    );


    @ParameterizedTest
    @MethodSource("provideAdvertisements")
    void isSatisfiedBy_WhenAppropriatePriceAccordingToCategory_ShouldReturnTrue(Advertisement advertisement) {

    }

    private static Stream<Arguments> provideAdvertisements() {
        return Stream.of(
                Arguments.of(getAdvertisement().addParameter(Advertisement.Parameter.builder()
                        .label("Вид одежды")
                        .identity("women_clothes_type")
                        .value("Джинсы, брюки")
                        .build()
                ))
        );
    }

    private static Advertisement getAdvertisement() {
        return Advertisement.builder()
                .subject("Female clothes")
                .companyAd(false)
                .fullyFunctional(true)
                .priceInByn(new BigDecimal(70))
                .parameters(new ArrayList<>())
                .build();
    }
}