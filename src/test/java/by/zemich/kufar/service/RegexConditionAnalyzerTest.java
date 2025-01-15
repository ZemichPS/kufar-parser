//package by.zemich.kufar.service;
//
//import by.zemich.kufar.domain.model.Advertisement;
//import by.zemich.kufar.extensions.AdvertisementParameterResolver;
//import by.zemich.kufar.service.api.ProductConditionClassifier;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//import org.mockito.InjectMocks;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//class RegexConditionAnalyzerTest {
//
//    @InjectMocks
//    private RegexConditionAnalyzer analyzer;
//
//    @Spy
//    private List<ProductConditionClassifier> classifiers;
//
//    @ParameterizedTest(name = "line number-> {0}")
//    @CsvFileSource(
//            resources = "/not_functional_devices_description.csv",
//            numLinesToSkip = 1
//    )
//    public void isFullyFunctional(String number, String description) {
//
//        Advertisement advertisement = getAdvertisement();
//        advertisement.setDetails(description);
//        boolean result = analyzer.isFullyFunctional(advertisement);
//        assertFalse(result);
//    }
//
//    private static Advertisement getAdvertisement() {
//        return Advertisement.builder()
//                .subject("Female clothes")
//                .companyAd(false)
//                .category("17010")
//                .fullyFunctional(true)
//                .parameters(new ArrayList<>())
//                .build();
//    }
//
//}