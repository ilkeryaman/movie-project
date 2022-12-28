package com.eri.service.cache.impl;

import com.eri.constant.enums.CacheKey;
import com.eri.constant.enums.CacheName;
import com.eri.exception.CacheNotInitializedException;
import com.eri.helper.MovieDataHelper;
import com.eri.model.Movie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CacheServiceImplTest {
    @InjectMocks
    CacheServiceImpl cacheService;

    //region mocks
    @Mock
    CacheManager cacheManager;
    //endregion mocks

    //region fields
    private MovieDataHelper dataHelper;
    private List<Movie> movieList;
    private String cacheName = CacheName.MOVIE_CACHE.getName();
    private String cacheKey = CacheKey.MOVIES.getName();
    //endregion fields

    @Before
    public void init(){
        dataHelper = new MovieDataHelper();
        movieList = dataHelper.getMovieList();
    }

    //region findListFromCacheWithKey
    @Test
    public void findListFromCacheWithKeyTest(){
        ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache(cacheName);
        concurrentMapCache.put(cacheKey, movieList);
        // mocking
        Mockito.when(cacheManager.getCache(Mockito.eq(cacheName))).thenReturn(concurrentMapCache);
        // actual method call
        List<Movie> moviesActual = cacheService.findListFromCacheWithKey(cacheKey);
        // assertions
        Assert.assertEquals(movieList, moviesActual);
    }

    @Test
    public void findListFromCacheWithKeyNullCacheTest(){
        // mocking
        Mockito.when(cacheManager.getCache(Mockito.eq(cacheName))).thenReturn(null);
        // actual method call
        List<Movie> moviesActual = cacheService.findListFromCacheWithKey(cacheKey);
        // assertions
        Assert.assertNull(moviesActual);
    }
    //endregion findListFromCacheWithKey

    //region findFromCacheWithKey
    @Test
    public void findFromCacheWithKeyTest(){
        ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache(cacheName);
        Movie movie = movieList.get(0);
        concurrentMapCache.put(cacheKey, movie);
        // mocking
        Mockito.when(cacheManager.getCache(Mockito.eq(cacheName))).thenReturn(concurrentMapCache);
        // actual method call
        Movie movieActual = cacheService.findFromCacheWithKey(cacheKey, Movie.class);
        // assertions
        Assert.assertEquals(movie, movieActual);
    }

    @Test
    public void findFromCacheWithKeyNullCacheTest(){
        // mocking
        Mockito.when(cacheManager.getCache(Mockito.eq(cacheName))).thenReturn(null);
        // actual method call
        Movie movieActual = cacheService.findFromCacheWithKey(cacheKey, Movie.class);
        // assertions
        Assert.assertNull(movieActual);
    }
    //endregion findFromCacheWithKey

    //region putCache
    @Test
    public void putCacheTest(){
        ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache(cacheName);
        // mocking
        Mockito.when(cacheManager.getCache(Mockito.eq(cacheName))).thenReturn(concurrentMapCache);
        // actual method call
        cacheService.putCache(cacheKey, movieList);
        // assertions
        Assert.assertEquals(movieList, concurrentMapCache.get(cacheKey).get());
    }
    //endregion putCache

    //region evictCache
    @Test
    public void evictCacheTest(){
        ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache(cacheName);
        concurrentMapCache.put(cacheKey, movieList);
        // mocking
        Mockito.when(cacheManager.getCache(Mockito.eq(cacheName))).thenReturn(concurrentMapCache);
        // actual method call
        cacheService.evictCache(cacheKey);
        // assertions
        Assert.assertNull(concurrentMapCache.get(cacheKey));
    }

    @Test(expected = CacheNotInitializedException.class)
    public void evictCacheWithNullCacheTest(){
        // mocking
        Mockito.when(cacheManager.getCache(Mockito.eq(cacheName))).thenReturn(null);
        // actual method call
        cacheService.evictCache(cacheKey);
    }
    //endregion evictCache
}
