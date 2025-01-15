package by.zemich.kufar.service.conditionanalizers;

import by.zemich.kufar.dao.entity.Advertisement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CommonConditionClassifierTest {

    private final CommonConditionClassifier classifier = new CommonConditionClassifier();

    @ParameterizedTest(name = "line number-> {0}, fraze-> {1}")
    @CsvFileSource(
            resources = "/common_defects.csv",
            delimiter = ';'
    )
    void analyze(int number, String details) {
        Advertisement advertisement = getAdvertisement();
        advertisement.setDetails(details);
        Assertions.assertTrue(classifier.analyze(advertisement));
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