package by.zemich.kufar.model.criterias;

import by.zemich.kufar.dao.entity.Advertisement;

public class NotCompanyCriteria implements Criteria {

    @Override
    public boolean isSatisfied(Advertisement advertisement) {
        return !advertisement.isCompanyAd();
    }
}
