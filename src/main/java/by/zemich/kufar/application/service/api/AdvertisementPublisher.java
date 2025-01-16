package by.zemich.kufar.application.service.api;

import by.zemich.kufar.domain.model.Advertisement;

public interface AdvertisementPublisher {
    boolean publish(Advertisement advertisement);
}
