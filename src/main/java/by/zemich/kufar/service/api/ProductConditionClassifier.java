package by.zemich.kufar.service.api;

import by.zemich.kufar.dao.entity.Advertisement;

public interface ProductConditionClassifier {
    boolean analyze(Advertisement advertisement);

    boolean isApplicable(Advertisement advertisement);
}
