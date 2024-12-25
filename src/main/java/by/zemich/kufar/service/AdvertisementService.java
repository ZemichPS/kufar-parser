package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.jparepository.AdvertisementRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository adsRepository;
    private final ObjectMapper jsonMapper;

    public Advertisement save(Advertisement advertisement) {
        return adsRepository.save(advertisement);
    }

    public Page<Advertisement> get(Pageable pageable) {
        return adsRepository.findAll(pageable);
    }

    public List<Advertisement> getAll() {
        return adsRepository.findAll();
    }

    public List<Advertisement> getAllByBrandAndModel(String brand, String model) {
        String parameters = createParameters(
                List.of(new Advertisement.Parameter("phones_brand", brand),
                        new Advertisement.Parameter("phones_model", model))
        );
        return adsRepository.findAllByParameters(parameters);
    }

    public List<Advertisement> getAllByBrandAndModelWithMemoryAmount(String brand, String model, String memoryAmount) {
        String parameters = createParameters(
                List.of(new Advertisement.Parameter("phones_brand", brand),
                        new Advertisement.Parameter("phones_model", model),
                        new Advertisement.Parameter("phablet_phones_memory", memoryAmount)
                )
        );
        return adsRepository.findAllByParameters(parameters);
    }

    public List<Advertisement> getAllByParameters(List<Advertisement.Parameter> parameters) {
        return adsRepository.findAllByParameters(createParameters(parameters));
    }


    public Advertisement getById(UUID id) {
        return adsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Advertisement not found"));
    }

    public void delete(UUID id) {
        adsRepository.deleteById(id);
    }

    public boolean existsByAdId(Long adId) {
        return adsRepository.existsByAdId(adId);
    }

    private String createParameters(List<Advertisement.Parameter> parameters) {
        try {
            return jsonMapper.writeValueAsString(parameters);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
