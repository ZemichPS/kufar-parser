package by.zemich.kufar.service.conditionanalizers;

import by.zemich.kufar.domain.service.conditionanalizers.classifiers.CameraConditionClassifier;
import by.zemich.kufar.domain.model.Advertisement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CameraConditionClassifierTest {

    private final CameraConditionClassifier classifier = new CameraConditionClassifier();

    @ParameterizedTest(name = "line number-> {0}")
    @CsvFileSource(
            resources = "/camera_defect_details.csv",
            numLinesToSkip = 1,
            delimiter = ';'
    )
    void analyzeTest_WhenCameraDefected_thenReturnFalse(String number, String description) {
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