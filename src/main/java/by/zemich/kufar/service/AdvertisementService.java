package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.repository.AdvertisementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository adRepository;

    public Advertisement save(Advertisement advertisement) {
        return adRepository.save(advertisement);
    }

    public Page<Advertisement> get(Pageable pageable) {
        return adRepository.findAll(pageable);
    }

    public Advertisement getById(UUID id) {
        return adRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Advertisement not found"));
    }

    public void delete(UUID id) {
        adRepository.deleteById(id);
    }

    public boolean existsByAdId(long adId) {
        return adRepository.existsByAdId(adId);
    }
}
