package com.eri.service.cache.impl;

import com.eri.constant.enums.CacheName;
import com.eri.constant.enums.ErrorMessage;
import com.eri.exception.CacheNotInitializedException;
import com.eri.service.cache.ICacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class CacheServiceImpl implements ICacheService {
    private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

    @Resource
    CacheManager cacheManager;

    @Override
    public <T> List<T> findListFromCacheWithKey(String key) {
        return getFromCacheWithKey(key, List.class);
    }

    @Override
    public <T> T findFromCacheWithKey(String key, Class keyClass) {
        return getFromCacheWithKey(key, keyClass);
    }

    @Override
    public <T> void putCache(String key, T object) {
        cacheManager.getCache(CacheName.MOVIE_CACHE.getName()).put(key, object);
    }

    @Override
    public void evictCache(String key) throws CacheNotInitializedException {
        if (Objects.isNull(cacheManager.getCache(CacheName.MOVIE_CACHE.getName()))) {
            logger.error(ErrorMessage.CACHE_INITIALIZATION_PROBLEM.getValue());
            throw new CacheNotInitializedException();
        } else {
            cacheManager.getCache(CacheName.MOVIE_CACHE.getName()).evict(key);
        }
    }

    private <T> T getFromCacheWithKey(String key, Class keyClass) {
        if (Objects.isNull(cacheManager.getCache(CacheName.MOVIE_CACHE.getName()))) {
            return null;
        } else {
            return (T) cacheManager.getCache(CacheName.MOVIE_CACHE.getName()).get(key, keyClass);
        }
    }
}
