package by.zemich.kufar.domain.service.conditionanalizers.classifiers.api;

import by.zemich.kufar.domain.model.Advertisement;

public interface ProductConditionClassifier {
    boolean analyze(Advertisement advertisement);

    boolean isApplicable(Advertisement advertisement);
}
