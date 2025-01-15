package by.zemich.kufar.application.service.api;

public interface PhotoMessenger<T> {
    void sendPhoto(T message);
}
