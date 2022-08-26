package com.eri.util;

import com.eri.constant.enums.CacheKey;
import com.eri.model.Movie;
import com.eri.service.cache.ICacheService;

import java.util.List;

public class CacheUtil {
    public static void cacheIfNeeded(ICacheService cacheService, List<Movie> moviesToCache){
        String key = CacheKey.MOVIES.getName();
        if(cacheService.findListFromCacheWithKey(key) == null){
            cacheService.putCache(key, moviesToCache);
        }
    }
}
