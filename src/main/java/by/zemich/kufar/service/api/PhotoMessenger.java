package by.zemich.kufar.service.api;

public interface PhotoMessenger<T> {
    void sendPhoto(T message);
}
