package by.zemich.kufar.application.service;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.infrastructure.clients.dto.AdDetailsDTO;
import by.zemich.kufar.domain.model.ComputedPriceStatistics;
import by.zemich.kufar.domain.policy.MinimumRequredAmountOfDataForMarketPriceCountingPolicy;
import by.zemich.kufar.domain.service.PriceAnalyzer;
import by.zemich.kufar.domain.service.conditionanalizers.ConditionAnalyzer;
import by.zemich.kufar.infrastructure.clients.KufarClient;
import by.zemich.kufar.infrastructure.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdvertisementServiceFacade {
    private final AdvertisementService advertisementService;
    private final ConditionAnalyzer conditionAnalyzer;
    private final KufarClient kufarClient;
    private final ModelService modelService;
    private final PriceAnalyzer priceAnalyzer;
    private final MinimumRequredAmountOfDataForMarketPriceCountingPolicy minDataSizePolicy = new MinimumRequredAmountOfDataForMarketPriceCountingPolicy();

    private final Predicate<Advertisement> fullFunctionalPredicate = Advertisement::isFullyFunctional;
    private final Predicate<Advertisement> commerceAdPredicate = Advertisement::isCompanyAd;
    private final Predicate<Advertisement> notCommerceAdPredicate = advertisement -> !advertisement.isCompanyAd();

    public Optional<ComputedPriceStatistics> getPriceStatisticsByModel(Advertisement advertisement) {

        if (!advertisement.isFullyFunctional()) return Optional.empty();
        if (advertisement.getBrand().isEmpty() || advertisement.getModel().isEmpty()) return Optional.empty();

        String brand = advertisement.getBrand().orElse("");
        String model = advertisement.getModel().orElse("");

        String memoryAmount = advertisement.getParameterValueByParameterName("phablet_phones_memory").orElse("");
        if (memoryAmount.isEmpty()) return Optional.empty();

        List<Advertisement> advertisements = advertisementService.getAllByBrandAndModelWithMemoryAmount(brand, model, memoryAmount);
        if (!minDataSizePolicy.isSatisfiedBy(advertisements.size())) return Optional.empty();

        BigDecimal marketPriceForCommerce = getMarketPrice(
                advertisements,
                fullFunctionalPredicate.and(commerceAdPredicate),
                advertisement.getCondition()
        );
        BigDecimal marketPriceForNotCommerce = getMarketPrice(
                advertisements,
                fullFunctionalPredicate.and(notCommerceAdPredicate),
                advertisement.getCondition()
        );
        BigDecimal commonMarketPrice = getMarketPrice(
                advertisements,
                fullFunctionalPredicate,
                advertisement.getCondition()
        );

        return Optional.of(new ComputedPriceStatistics(
                marketPriceForCommerce,
                marketPriceForNotCommerce,
                commonMarketPrice)
        );
    }

    public void updateAdvertisementCauseNewConditionRules() {
        advertisementService.getAll().stream().parallel()
                .filter(advertisement -> advertisement.getCategory().equalsIgnoreCase("17010"))
                .peek(advertisement -> {
                    boolean result = conditionAnalyzer.isFullyFunctional(advertisement);
                    advertisement.setFullyFunctional(result);
                }).toList().stream().parallel()
                .forEach(advertisementService::save);
    }

    public void parseSmartphonesAdsToDB() {
        modelService.getAll().stream()
                .map(model -> {
                    Long manufactureId = model.getManufacturer().getId();
                    String modelKufarId = model.getKufarId();
                    Map<String, String> paramMap = new HashMap<>();
                    paramMap.put("cat", "17010");
                    paramMap.put("lang", "ru");
                    paramMap.put("cmp", "0");
                    paramMap.put("pb", manufactureId.toString());
                    paramMap.put("phm", modelKufarId);
                    paramMap.put("prn", "17000");
                    paramMap.put("size", "100");
                    paramMap.put("sort", "lst.d");
                    return paramMap;
                })
                .map(kufarClient::getAdsByParameters)
                .filter(Objects::nonNull)
                .flatMap(adsDTO -> adsDTO.getAds().stream().parallel())
                .filter(Objects::nonNull)
                .filter(adsDTO -> !advertisementService.existsByAdId(adsDTO.getAdId()))
                .map(adDTO -> {
                    Advertisement advertisement = Mapper.mapToEntity(adDTO);
                    adDTO.getAdParameters().stream()
                            .map(Mapper::mapToEntity)
                            .forEach(advertisement::addParameter);
                    return advertisement;
                })
                .peek(advertisement -> {
                    AdDetailsDTO detailsDTO = kufarClient.getDetails(advertisement.getAdId());
                    String details = detailsDTO.getResult().getBody();
                    advertisement.setDetails(details);
                    advertisement.setFullyFunctional(conditionAnalyzer.isFullyFunctional(advertisement));
                })
                .forEach(advertisementService::save);
    }

    private BigDecimal getMarketPrice(List<Advertisement> advertisements,
                                      Predicate<Advertisement> predicate,
                                      String condition
    ) {
        List<BigDecimal> prices = advertisements.stream()
                .filter(predicate)
                .filter(ad -> ad.getCondition().equals(condition))
                .map(Advertisement::getPriceInByn)
                .toList();

        if (!minDataSizePolicy.isSatisfiedBy(prices.size())) return BigDecimal.ZERO;
        return priceAnalyzer.getMarketPrice(prices);
    }

}
