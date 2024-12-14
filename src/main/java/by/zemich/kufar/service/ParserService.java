package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.repository.AdvertisementRepository;
import by.zemich.kufar.dto.AdsResponseDTO;
import by.zemich.kufar.input.telegram.TelegramBot;
import by.zemich.kufar.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParserService {
    private final AdvertisementRepository repository;
    private final RestTemplate restTemplate;

    @Scheduled(fixedRate = 10_000, initialDelay = 5_000)
    public void parse() {
        String url = "https://api.kufar.by/search-api/v2/search/rendered-paginated";

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("cat", "17010")
                .queryParam("lang", "17000")
                .queryParam("size", "40")
                .queryParam("sort", "lst.d")
                .build().toUri();
        AdsResponseDTO dto = restTemplate.getForObject(uri, AdsResponseDTO.class);
        log.info(dto.toString());
    }



    public AdsResponseDTO parser() {
        String url = "https://api.kufar.by/search-api/v2/search/rendered-paginated";

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("cat", "17010")
                .queryParam("lang", "17000")
                .queryParam("size", "40")
                .queryParam("sort", "lst.d")
                .build().toUri();

        AdsResponseDTO response = restTemplate.getForObject(uri, AdsResponseDTO.class);
        response.getAds().forEach(this::save);
        return response;
    }

    private void save(AdsResponseDTO.AdDTO adDTO) {
            Advertisement advertisement = Mapper.mapToEntity(adDTO);
            adDTO.getAdParameters().stream()
                    .map(Mapper::mapToEntity)
                    .forEach(advertisement::addParameter);
            repository.save(advertisement);
    }
}
