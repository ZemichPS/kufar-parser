package by.zemich.kufar.application.service;

import by.zemich.kufar.domain.model.GeoData;
import by.zemich.kufar.infrastructure.repository.jparepository.GeoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<GeoData> findAll() {
        return geoRepository.findAll();
    }

    public List<GeoData> findAllRegions(){
        return geoRepository.findAllByRegions();
    }

    public List<GeoData> findAllSettlementsByRegionNumber(Integer regionNumber) {
        return geoRepository.findAllSettlementsByRegionNumber(regionNumber);
    }




}
