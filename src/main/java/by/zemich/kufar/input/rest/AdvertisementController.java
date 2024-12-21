package by.zemich.kufar.input.rest;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.entity.GeoData;
import by.zemich.kufar.dto.AdDetailsDTO;
import by.zemich.kufar.dto.AdsDTO;
import by.zemich.kufar.dto.FilterDto;
import by.zemich.kufar.dto.GeoDataDTO;
import by.zemich.kufar.service.AdvertisementService;
import by.zemich.kufar.service.GeoService;
import by.zemich.kufar.service.ScheduledService;
import by.zemich.kufar.service.PriceAnalyzer;
import by.zemich.kufar.service.clients.KufarClient;
import by.zemich.kufar.service.clients.ManufacturerDto;
import by.zemich.kufar.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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

    @GetMapping(
            produces = "application/json",
            value = "/ads"
    )
    public ResponseEntity<AdsDTO> getAll() {
        AdsDTO dto = kufarClient.getNewAds();
        return ok(dto);
    }

    @GetMapping(
            produces = "application/json",
            value = "/ads/{id}"
    )
    public ResponseEntity<AdDetailsDTO> getAdDetails(@PathVariable Long id) {
        AdDetailsDTO dto = kufarClient.getDetails(id);
        return ok(dto);
    }

    @GetMapping(
            produces = "application/json",
            value = "/geos"
    )
    public ResponseEntity<List<GeoDataDTO>> getGeos() {
        final List<GeoDataDTO> geoData = kufarClient.getGeoData();
        geoData.stream()
                .filter(dto -> !geoService.existsById(dto.getId()))
                .map(Mapper::mapToEntity)
                .forEach(geoService::save);
        return ok(geoData);
    }

    @GetMapping(
            produces = "application/json",
            value = "/brands"
    )
    public ResponseEntity<List<FilterDto.RuleWrapper>> getBrands() {
        List<FilterDto.RuleWrapper>  filledManufacture = kufarClient.getFilledManufacture();
        return ok(filledManufacture);
    }

    @GetMapping(
            produces = "application/json",
            value = "/geos/all_regions"
    )
    public ResponseEntity<List<GeoData>> findAllRegions() {
        final List<GeoData> allRegions = geoService.findAllRegions();
        return ResponseEntity.ok(allRegions);
    }

    @GetMapping(
            produces = "application/json",
            value = "/geos/all/{settlementId}"
    )
    public ResponseEntity<List<GeoData>> findAllSettlementsByRegionNumber(@PathVariable Integer settlementId) {
        final List<GeoData> allRegions = geoService.findAllSettlementsByRegionNumber(settlementId);
        return ResponseEntity.ok(allRegions);
    }

    @GetMapping(
            produces = "application/json",
            value = "/geos/all"
    )
    public ResponseEntity<List<GeoData>> findAll() {
        final List<GeoData> allRegions = geoService.findAll();
        return ResponseEntity.ok(allRegions);
    }

    @GetMapping(
            produces = "application/json",
            value = "/filter"
    )
    public ResponseEntity<FilterDto> filter() {
        return ResponseEntity.ok(kufarClient.getFilters());
    }



    @GetMapping(
            produces = "application/json",
            value = "/getAndSave"
    )
    public ResponseEntity<AdsDTO> getAdsAndSave() {
        kufarClient.getNewAds();
        return ResponseEntity.ok(kufarClient.getNewAds());
    }

    @GetMapping(
            produces = "application/json",
            value = "/get_all_details"
    )
    public ResponseEntity<List<String>> getAllDetails() {
        return ResponseEntity.ok(advertisementService.getAll().stream()
                .map(Advertisement::getDetails)
                .toList()
        );
    }


    @GetMapping(
            produces = "application/json",
            value = "/get_market_price_by_model/{model}"
    )
    public ResponseEntity<BigDecimal> getMarketPriceByModel(@PathVariable String model) {
        AdsDTO ads = kufarClient.getAdsByModelAndPageNumber(model, 340);
        List<BigDecimal> list = ads.getAds().stream().map(AdsDTO.AdDTO::getPriceByn).filter(price-> price.compareTo(BigDecimal.valueOf(150))>= 0).toList();
        BigDecimal result = priceAnalyzer.getMarketPrice(list);
        return ResponseEntity.ok(result);
    }

}
