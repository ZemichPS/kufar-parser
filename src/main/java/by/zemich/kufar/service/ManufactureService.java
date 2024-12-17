package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Manufacturer;
import by.zemich.kufar.dao.entity.Model;
import by.zemich.kufar.dao.repository.ManufacturerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ManufactureService {
    private final ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public List<Model> getAllModelsByManufacturer(Long manufacturerId) {
        return manufacturerRepository
                .getReferenceById(manufacturerId)
                .getModels();
    }

}
