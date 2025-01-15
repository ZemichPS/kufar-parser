package by.zemich.kufar.application.service;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.infrastructure.repository.jparepository.AdvertisementRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private final AdvertisementRepository adsRepository;
    private final ObjectMapper jsonMapper;

    @CachePut(value = "advertisements", key = "#advertisement.id")
    public Advertisement save(Advertisement advertisement) {
        return adsRepository.save(advertisement);
    }

    @Cacheable(
            value = "advertisements",
            key = "'pageable-' + #pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<Advertisement> get(Pageable pageable) {
        return adsRepository.findAll(pageable);
    }

    @Cacheable("advertisements")
    public List<Advertisement> getAll() {
        return adsRepository.findAll();
    }

    @Cacheable(
            value = "advertisements",
            key = "'brand-' + #brand + '-model-' + #model"
    )
    public List<Advertisement> getAllByBrandAndModel(String brand, String model) {
        String parameters = createParameters(
                List.of(new Advertisement.Parameter("phones_brand", brand),
                        new Advertisement.Parameter("phones_model", model))
        );
        return adsRepository.findAllByParameters(parameters);
    }

    @Cacheable(
            value = "advertisements",
            key = "'brand-' + #brand + '-model-' + #model + '-memory-' + #memoryAmount"
    )
    public List<Advertisement> getAllByBrandAndModelWithMemoryAmount(String brand, String model, String memoryAmount) {
        String parameters = createParameters(
                List.of(new Advertisement.Parameter("phones_brand", brand),
                        new Advertisement.Parameter("phones_model", model),
                        new Advertisement.Parameter("phablet_phones_memory", memoryAmount)
                )
        );
        return adsRepository.findAllByParameters(parameters);
    }

    @Cacheable(value = "advertisements", key = "'params-' + #parameters.hashCode()")
    public List<Advertisement> getAllByParameters(List<Advertisement.Parameter> parameters) {
        return adsRepository.findAllByParameters(createParameters(parameters));
    }

    @Cacheable(value = "advertisements", key = "#id")
    public Advertisement getById(UUID id) {
        return adsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Advertisement not found"));
    }

    @CacheEvict(value = "advertisements", key = "#id")
    public void delete(UUID id) {
        adsRepository.deleteById(id);
    }

    public boolean existsByAdId(Long adId) {
        return adsRepository.existsByAdId(adId);
    }


    @Cacheable(
            value = "advertisements",
            key = "'publishedAt-' + #dateTime + '-adId-' + #adId + '-category-' + #category"
    )
    public boolean existsByPublishedAt(LocalDateTime dateTime, Long adId, String category) {
        return adsRepository.existsByPublishedAtAndAdIdAndCategory(dateTime, adId, category);
    }

    private String createParameters(List<Advertisement.Parameter> parameters) {
        try {
            return jsonMapper.writeValueAsString(parameters);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
