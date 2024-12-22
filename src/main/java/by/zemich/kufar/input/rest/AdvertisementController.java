package by.zemich.kufar.input.rest;

import by.zemich.kufar.service.AdvertisementService;
import by.zemich.kufar.service.GeoService;
import by.zemich.kufar.service.ScheduledService;
import by.zemich.kufar.service.PriceAnalyzer;
import by.zemich.kufar.service.clients.KufarClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
