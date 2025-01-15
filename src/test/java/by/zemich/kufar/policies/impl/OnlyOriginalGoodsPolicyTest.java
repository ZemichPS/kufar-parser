package by.zemich.kufar.policies.impl;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.policy.OnlyOriginalGoodsPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

class OnlyOriginalGoodsPolicyTest {

    private final OnlyOriginalGoodsPolicy policy = new OnlyOriginalGoodsPolicy();

    @ParameterizedTest
    @MethodSource("getRelevantDescriptions")
    void isSatisfiedBy_WhenProductIsReplica_thenReturnFalse(String description) {
        Advertisement advertisement = getAdvertisement();
        advertisement.setDetails(description);
        Assertions.assertFalse(policy.isSatisfiedBy(advertisement));
    }

    @ParameterizedTest
    @MethodSource("getNonRelevantDescriptions")
    void isSatisfiedBy_WhenProductNotReplica_thenReturnTrue(String description) {
        Advertisement advertisement = getAdvertisement();
        advertisement.setDetails(description);
        Assertions.assertTrue(policy.isSatisfiedBy(advertisement));
    }


    private static Advertisement getAdvertisement() {
        return Advertisement.builder()
                .subject("Apple Iphone 13pro")
                .companyAd(false)
                .fullyFunctional(true)
                .parameters(new ArrayList<>())
                .build();
    }

    private static Stream<Arguments> getRelevantDescriptions() {
        return Stream.of(
                Arguments.of("Продам копию"),
                Arguments.of("копия"),
                Arguments.of("Копия"),
                Arguments.of("IPHONE 15 Pro Max(копия)"),
                Arguments.of("просто копия"),
                Arguments.of("паль"),
                Arguments.of("подделка"),
                Arguments.of("поделка"),
                Arguments.of("хорошая реплика"),
                Arguments.of("качественная реплика")
        );
    }

    private static Stream<Arguments> getNonRelevantDescriptions() {
        return Stream.of(
                Arguments.of("Оригинальный товар"),
                Arguments.of("100% оригинал"),
                Arguments.of("Настоящий iPhone"),
                Arguments.of("Не реплика"),
                Arguments.of("Без реплик и копий")
        );
    }

}