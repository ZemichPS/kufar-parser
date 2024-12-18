package by.zemich.kufar.service.api;

public interface TextMessenger<T> {
    void sendText(T message);
}
