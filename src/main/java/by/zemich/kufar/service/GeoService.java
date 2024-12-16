package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.GeoData;
import by.zemich.kufar.dao.repository.GeoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoService {
    private final GeoRepository geoRepository;

    public GeoData save(GeoData geoData) {
        return geoRepository.save(geoData);
    }

    public boolean existsById(Integer id) {
        return geoRepository.existsById(id);
    }

    List<GeoData> findAll() {
        return geoRepository.findAll();
    }

    List<GeoData> findAllRegions(){
        return geoRepository.findAllByRegionNumber(1);
    }

    List<GeoData> findAllSettlementsByRegionNumber(Integer regionNumber) {
        return geoRepository.findAllSettlementsByRegionNumber(regionNumber);
    }




}
