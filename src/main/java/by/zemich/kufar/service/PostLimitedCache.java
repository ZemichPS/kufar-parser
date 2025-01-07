package by.zemich.kufar.service;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class PostLimitedCache<K, V> extends ConcurrentHashMap<K, V> {
    private final int maxSize;
    private final Lock lock = new ReentrantLock();

    public PostLimitedCache(int maxSize) {
        super(16, 0.75f);
        this.maxSize = maxSize;
    }

    @Override
    public V put(K key, V value) {
        lock.lock();
        try {
            // Удаляем "старейший" элемент, если размер превышен
            if (size() >= maxSize) {
                K oldestKey = keys().nextElement(); // Получаем случайный ключ
                remove(oldestKey);
            }
            return super.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V putIfAbsent(K key, V value) {
        lock.lock();
        try {
            if (size() >= maxSize) {
                K oldestKey = keys().nextElement();
                remove(oldestKey);
            }
            return super.putIfAbsent(key, value);
        } finally {
            lock.unlock();
        }
    }
}
