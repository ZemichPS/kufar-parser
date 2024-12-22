package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Manufacturer;
import by.zemich.kufar.dao.entity.Model;
import by.zemich.kufar.dao.jparepository.ManufacturerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManufactureService {
    private final ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public List<Model> getAllModelsByManufacturer(String manufacturerName) {
        return manufacturerRepository.findByName(manufacturerName)
                .map(Manufacturer::getModels)
                .orElseThrow();
    }
    public List<Model> getAllModelsByManufacturer(Long manufacturerId) {
        return manufacturerRepository
                .findById(manufacturerId)
                .map(Manufacturer::getModels)
                .orElseThrow();
    }

    public boolean existsById(Long id) {
        return manufacturerRepository.existsById(id);
    }

    public Optional<Manufacturer> getById(Long id) {
        return manufacturerRepository.findById(id);
    }

    public Manufacturer save(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }



}
