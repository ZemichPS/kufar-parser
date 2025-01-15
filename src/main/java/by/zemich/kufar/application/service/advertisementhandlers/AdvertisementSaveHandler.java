package by.zemich.kufar.application.service.advertisementhandlers;

import by.zemich.kufar.application.service.advertisementhandlers.api.AdvertisementHandler;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.application.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdvertisementSaveHandler implements AdvertisementHandler {

    private final AdvertisementService advertisementService;

    @Override
    public Advertisement handle(Advertisement advertisement) {
        return advertisementService.save(advertisement);
    }

    @Override
    public boolean canHandle(Advertisement advertisement) {
        return true;
    }
}
