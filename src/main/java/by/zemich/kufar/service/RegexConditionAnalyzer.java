package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.ConditionAnalyzer;
import by.zemich.kufar.service.api.ProductConditionClassifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RegexConditionAnalyzer implements ConditionAnalyzer {

    private final List<ProductConditionClassifier> classifiers;


    @Override
    public boolean isFullyFunctional(Advertisement advertisement) {
        return classifiers.stream()
                .noneMatch(classifier -> classifier.analyze(advertisement));
    }
}



