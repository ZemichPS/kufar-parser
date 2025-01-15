package by.zemich.kufar.application.service.advertisementhandlers.api;

import by.zemich.kufar.domain.model.Advertisement;

public interface AdvertisementHandler {
    Advertisement handle(Advertisement advertisement);

    boolean canHandle(Advertisement advertisement);
}
