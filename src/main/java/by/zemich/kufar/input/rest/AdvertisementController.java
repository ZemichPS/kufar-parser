package by.zemich.kufar.input.rest;

import by.zemich.kufar.dto.AdsResponseDTO;
import by.zemich.kufar.service.ParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/advertisement")
@RequiredArgsConstructor
public class AdvertisementController {

    private final ParserService parserService;

    @GetMapping(
            produces = "application/json"
    )
    public ResponseEntity<AdsResponseDTO> getAll(){
        AdsResponseDTO response = parserService.parser();
        return ok(response);
    }


}
