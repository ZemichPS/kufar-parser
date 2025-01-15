package by.zemich.kufar.service.conditionanalizers;

import by.zemich.kufar.domain.service.conditionanalizers.classifiers.ScreenConditionClassifier;
import by.zemich.kufar.domain.model.Advertisement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;

class ScreenConditionClassifierTest {

    private final ScreenConditionClassifier classifier = new ScreenConditionClassifier();

    @ParameterizedTest(name = "line number-> {0}, fraze-> {1}")
    @CsvFileSource(
            resources = "/screen_defect_details.csv",
            delimiter = ';'
    )
    void analyze(int number, String description) {

        Advertisement advertisement = getAdvertisement();
        advertisement.setDetails(description);
        boolean result = classifier.analyze(advertisement);
        Assertions.assertTrue(result);
    }

    private static Advertisement getAdvertisement() {
        return Advertisement.builder()
                .subject("Some device")
                .companyAd(false)
                .category("17010")
                .fullyFunctional(true)
                .parameters(new ArrayList<>())
                .build();
    }
}