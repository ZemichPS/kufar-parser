package by.zemich.kufar.application.service.api;

import java.math.BigDecimal;
import java.util.Map;

public interface CategoryPriceListRepository {

    Map<String, BigDecimal> getCategoryShoesPriceList();

    Map<String, BigDecimal> getCategoryClothesPriceList();

}
