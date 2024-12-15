package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dto.AdsResponseDTO;
import by.zemich.kufar.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdParserService {
    private final AdvertisementService advertisementService;
    private final RestTemplate restTemplate;
    private final NotificationService notificationService;

    public AdsResponseDTO parseAndSaveIfNotExists() {
        String url = "https://api.kufar.by/search-api/v2/search/rendered-paginated";

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("cat", "17010")
                .queryParam("lang", "17000")
                .queryParam("size", "40")
                .queryParam("sort", "lst.d")
                .build().toUri();

        AdsResponseDTO response = restTemplate.getForObject(uri, AdsResponseDTO.class);
        response.getAds().stream()
                .filter(dto -> !advertisementService.existsByAdId(dto.getAdId()))
                .map(this::save)
                .forEach(this::notify);
        return response;
    }

    private Advertisement save(AdsResponseDTO.AdDTO adDTO) {
        Advertisement advertisement = Mapper.mapToEntity(adDTO);
        adDTO.getAdParameters().stream()
                .map(Mapper::mapToEntity)
                .forEach(advertisement::addParameter);
        return advertisementService.save(advertisement);
    }

    public void notify(Advertisement advertisement) {
        notificationService.notify(advertisement);
    }

}
