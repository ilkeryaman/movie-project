package com.eri.service.messaging.impl;

import com.eri.constant.enums.CacheKey;
import com.eri.model.Movie;
import com.eri.service.cache.ICacheService;
import com.eri.service.messaging.IMessageManagerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageManagerServiceImpl implements IMessageManagerService {
    @Resource
    private ICacheService cacheService;

    @Override
    public void addMovieToNewcomersCache(Movie movie) {
        String cacheKey = CacheKey.NEWCOMERS.getName();
        List<Movie> newComers = cacheService.findListFromCacheWithKey(cacheKey);
        if(newComers == null){
            newComers = new ArrayList<>();
        }
        newComers.add(movie);
        cacheService.putCache(cacheKey, newComers);
    }
}
