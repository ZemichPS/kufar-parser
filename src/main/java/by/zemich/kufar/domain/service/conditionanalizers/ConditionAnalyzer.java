package by.zemich.kufar.domain.service.conditionanalizers;

import by.zemich.kufar.domain.model.Advertisement;

public interface ConditionAnalyzer {
    boolean isFullyFunctional(Advertisement advertisement);
}
