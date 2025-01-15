package by.zemich.kufar.application.service.api;

import by.zemich.kufar.domain.model.Notification;

public interface Notifiable {
    void notify(Notification notification);

    String getNotifierId();
}
