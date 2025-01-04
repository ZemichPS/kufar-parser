package by.zemich.kufar.service.api;

import by.zemich.kufar.dao.entity.Notification;

public interface Notifiable {
    void notify(Notification notification);

    String getNotifierId();
}
