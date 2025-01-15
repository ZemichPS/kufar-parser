package by.zemich.kufar.presentation.controller;

import by.zemich.kufar.application.service.*;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.infrastructure.clients.dto.AdsDTO;
import by.zemich.kufar.infrastructure.clients.dto.CategoriesDto;
import by.zemich.kufar.domain.service.PriceAnalyzer;
import by.zemich.kufar.infrastructure.scheduler.ScheduledService;
import by.zemich.kufar.infrastructure.clients.KufarClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final ScheduledService adParserService;
    private final GeoService geoService;
    private final KufarClient kufarClient;
    private final AdvertisementService advertisementService;
    private final PriceAnalyzer priceAnalyzer;
    private final ScheduledService scheduledService;
    private final AdvertisementServiceFacade advertisementServiceFacade;


    @GetMapping(
            produces = "application/json",
            value = "/update_models"
    )
    public ResponseEntity<Void> updateModels() {
        scheduledService.getAndUpdateManufacturesAndModelsList();
        return ResponseEntity.noContent().build();
    }

    @GetMapping(
            produces = "application/json",
            value = "/update_geo_data"
    )
    public ResponseEntity<Void> getUpdateGeoData() {
        scheduledService.updateGeoData();
        return ResponseEntity.noContent().build();
    }

    @GetMapping(
            produces = "application/json",
            value = "/get_new_ads"
    )
    public ResponseEntity<Void> getNewAdsAndSave() {
        scheduledService.getNewAdsAndSaveIfNotExists();
        return ResponseEntity.noContent().build();
    }

    @GetMapping(
            produces = "application/json",
            value = "/get_ad_by_brand_and_model"
    )
    public ResponseEntity<List<Advertisement>> getAdsByBrandAndModel(@RequestParam String brand, @RequestParam String model) {
        final List<Advertisement> allByBrandAndModel = advertisementService.getAllByBrandAndModel(brand, model);
        return ResponseEntity.ok(allByBrandAndModel);
    }


    @GetMapping(
            produces = "application/json",
            value = "/get_details"
    )
    public ResponseEntity<List<String>> getAdsByBrandAndModel() {
        List<String> details = advertisementService.getAll().stream()
                .filter(advertisement -> !advertisement.isCompanyAd())
                .map(Advertisement::getDetails)
                .toList();
        return ResponseEntity.ok(details);
    }

    @GetMapping(
            produces = "application/json",
            value = "/update_condition"
    )
    public ResponseEntity<Void> updateAdvertisementCondition() {
        advertisementServiceFacade.updateAdvertisementCauseNewConditionRules();
        return ResponseEntity.noContent().build();
    }

    @GetMapping(
            produces = "application/json",
            value = "/locations"
    )
    public ResponseEntity<List<String>> getLocationsFromAds() {
        List<String> list = advertisementService.getAll().stream().
                map(advertisement -> advertisement.getParameterValueByParameterName("area"))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .distinct()
                .toList();
        return ResponseEntity.ok(list);
    }


    @GetMapping(
            produces = "application/json",
            value = "/categories"
    )
    public ResponseEntity<CategoriesDto> getCategories() {
        return ResponseEntity.ok(kufarClient.getCategories());
    }

    @GetMapping("/get_ads_by_params")
    public ResponseEntity<AdsDTO> getAdsByParametersBody(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(kufarClient.getAdsByParameters(params));
    }

    @GetMapping("/parse_ads")
    public ResponseEntity<Void> parseAdsToDD() {
        advertisementServiceFacade.parseSmartphonesAdsToDB();
        return ResponseEntity.noContent().build();
    }


}
