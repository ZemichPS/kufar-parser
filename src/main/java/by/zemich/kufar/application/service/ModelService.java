package by.zemich.kufar.application.service;

import by.zemich.kufar.domain.model.Model;
import by.zemich.kufar.infrastructure.repository.jparepository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ModelService {
    private final ModelRepository modelRepository;

    public Optional<Model> getById(UUID id) {
        return modelRepository.findById(id);
    }

    public Optional<Model> getByName(String name) {
        return modelRepository.findByName(name);
    }

    public boolean existsByName(String name) {
        return modelRepository.existsByName(name);
    }

    public List<Model> getAll() {
        return modelRepository.findAll();
    }
}
