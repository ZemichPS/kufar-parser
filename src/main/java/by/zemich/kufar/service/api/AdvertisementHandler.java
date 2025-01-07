package by.zemich.kufar.service.api;

import by.zemich.kufar.dao.entity.Advertisement;

public interface AdvertisementHandler {
    Advertisement handle(Advertisement advertisement);

    boolean canHandle(Advertisement advertisement);
}
