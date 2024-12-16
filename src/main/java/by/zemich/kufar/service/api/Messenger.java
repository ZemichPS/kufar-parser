package by.zemich.kufar.service.api;

public interface Messenger<T> {
    void send(T message);
}
