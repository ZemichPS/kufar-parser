package by.zemich.kufar.input.rest;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.AdvertisementService;
import by.zemich.kufar.service.GeoService;
import by.zemich.kufar.service.ScheduledService;
import by.zemich.kufar.service.PriceAnalyzer;
import by.zemich.kufar.service.clients.KufarClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
