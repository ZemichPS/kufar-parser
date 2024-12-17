package by.zemich.kufar.input.rest;

import by.zemich.kufar.dao.entity.GeoData;
import by.zemich.kufar.dto.AdDetailsDTO;
import by.zemich.kufar.dto.AdsDTO;
import by.zemich.kufar.dto.FilterDto;
import by.zemich.kufar.dto.GeoDataDTO;
import by.zemich.kufar.service.GeoService;
import by.zemich.kufar.service.ParserService;
import by.zemich.kufar.service.clients.KufarClient;
import by.zemich.kufar.service.clients.ManufacturerDto;
import by.zemich.kufar.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class AdvertisementController {

    private final ParserService adParserService;
    private final GeoService geoService;
    private final KufarClient kufarClient;

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
    public ResponseEntity<AdDetailsDTO> getAdDetails(@PathVariable Integer id) {
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
                .filter(dto-> !geoService.existsById(dto.getId()))
                .map(Mapper::mapToEntity)
                .forEach(geoService::save);
        return ok(geoData);
    }

    @GetMapping(
            produces = "application/json",
            value = "/geos/all_regions"
    )
    public ResponseEntity<List<GeoData>> findAllRegions(){
        final List<GeoData> allRegions = geoService.findAllRegions();
        return ResponseEntity.ok(allRegions);
    }

    @GetMapping(
            produces = "application/json",
            value = "/geos/all/{settlementId}"
    )
    public ResponseEntity<List<GeoData>> findAllSettlementsByRegionNumber(@PathVariable Integer settlementId){
        final List<GeoData> allRegions = geoService.findAllSettlementsByRegionNumber(settlementId);
        return ResponseEntity.ok(allRegions);
    }

    @GetMapping(
            produces = "application/json",
            value = "/geos/all"
    )
    public ResponseEntity<List<GeoData>> findAll(){
        final List<GeoData> allRegions = geoService.findAll();
        return ResponseEntity.ok(allRegions);
    }

    @GetMapping(
            produces = "application/json",
            value = "/filter"
    )
    public ResponseEntity<FilterDto> filter(){
        return ResponseEntity.ok(kufarClient.getFilters());
    }

    @GetMapping(
            produces = "application/json",
            value = "/manufacture"
    )
    public ResponseEntity<List<ManufacturerDto>> manufacture(){
        return ResponseEntity.ok(kufarClient.getManufacture());
    }

}
