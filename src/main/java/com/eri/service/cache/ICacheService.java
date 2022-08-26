package com.eri.service.cache;

import com.eri.exception.CacheNotInitializedException;

import java.util.List;

public interface ICacheService {
    <T> List<T> findListFromCacheWithKey(String key);
    <T> T findFromCacheWithKey(String key, Class keyClass);

    <T> void putCache(String key, T object);
    void evictCache(String key) throws CacheNotInitializedException;
}
