package by.zemich.kufar.service.advertisementhandlers;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.AdvertisementService;
import by.zemich.kufar.service.api.AdvertisementHandler;
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
