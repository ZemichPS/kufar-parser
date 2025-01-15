package by.zemich.kufar.domain.model.criterias;

import by.zemich.kufar.domain.model.Advertisement;

public class NotCompanyCriteria implements Criteria {

    @Override
    public boolean isSatisfied(Advertisement advertisement) {
        return !advertisement.isCompanyAd();
    }
}
