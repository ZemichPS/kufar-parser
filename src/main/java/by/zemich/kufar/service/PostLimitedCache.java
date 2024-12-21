package by.zemich.kufar.service;

import java.util.LinkedHashMap;
import java.util.Map;


public class PostLimitedCache<K, V> extends LinkedHashMap<K, V> {
    private final int maxSize;

    public PostLimitedCache(int maxSize) {
        super(16, 0.75f, true);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }
}
