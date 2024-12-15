package by.zemich.kufar.input.rest;

import by.zemich.kufar.dto.AdsResponseDTO;
import by.zemich.kufar.service.AdParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/advertisement")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdParserService adParserService;

    @GetMapping(
            produces = "application/json"
    )
    public ResponseEntity<AdsResponseDTO> getAll(){
        AdsResponseDTO response = adParserService.parseAndSaveIfNotExists();
        return ok(response);
    }


}
