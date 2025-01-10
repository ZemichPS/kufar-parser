package by.zemich.kufar.service.api;

import by.zemich.kufar.dao.entity.Advertisement;

public interface ConditionAnalyzer {
    boolean isFullyFunctional(Advertisement advertisement);
}
