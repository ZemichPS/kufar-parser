package by.zemich.kufar.input.rest;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.*;
import by.zemich.kufar.service.clients.KufarClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class AdvertisementController {

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
    public ResponseEntity<Void> getUpdateModels() {
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
    public ResponseEntity<List<String>> getLocations() {
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
            value = "/by_locations"
    )
    public ResponseEntity<List<String>> getByLocations(@RequestParam String location) {
//        List<String> list = advertisementService.getAll().stream().
//                map(advertisement -> advertisement.getParameterValueByParameterName("area"))
//                .filter(Optional::isPresent)
//                .filter()
//                .map(Optional::get)
//                .distinct()
//                .toList();
//        return ResponseEntity.ok(list);
        return null;
    }

}
