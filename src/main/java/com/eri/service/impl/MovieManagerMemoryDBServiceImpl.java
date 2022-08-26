package com.eri.service.impl;

import com.eri.constant.enums.CacheKey;
import com.eri.exception.CacheNotInitializedException;
import com.eri.model.Movie;
import com.eri.service.MovieManagerService;
import com.eri.service.cache.ICacheService;
import com.eri.service.memorydb.IMovieMemoryDBService;
import com.eri.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("movieManagerMemoryDBService")
public class MovieManagerMemoryDBServiceImpl extends MovieManagerService {

    @Resource
    ICacheService cacheService;

    @Autowired
    IMovieMemoryDBService movieMemoryDBService;

    @Override
    public List<Movie> getMovies(boolean fromCache){
        return getMovies(fromCache, true);
    }

    @Override
    public List<Movie> getMovies() {
        return getMovies(false, false);
    }

    public List<Movie> getMovies(boolean fromCache, boolean enableCache) {
        if(fromCache){
            List<Movie> cachedMovies = cacheService.findListFromCacheWithKey(CacheKey.MOVIES.getName());
            if(cachedMovies == null){
                throw new CacheNotInitializedException();
            }
            return cachedMovies;
        }
        List<Movie> movies = movieMemoryDBService.getMovies();
        if(enableCache){
            CacheUtil.cacheIfNeeded(cacheService, new ArrayList<>(movies));
        }
        return movies;
    }
}
