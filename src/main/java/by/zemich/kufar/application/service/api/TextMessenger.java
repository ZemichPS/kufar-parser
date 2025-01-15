package by.zemich.kufar.application.service.api;

public interface TextMessenger<T> {
    void sendText(T message);
}
