package com.eri.service.messaging.impl;

import com.eri.constant.enums.CacheKey;
import com.eri.helper.MovieDataHelper;
import com.eri.model.Movie;
import com.eri.service.cache.ICacheService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MessageManagerServiceImplTest {
    @InjectMocks
    private MessageManagerServiceImpl messageManagerService;

    //region mocks
    @Mock
    private ICacheService cacheServiceMock;
    //endregion mocks

    //region fields
    private MovieDataHelper dataHelper;
    private List<Movie> movieList;
    //endregion fields

    @Before
    public void init(){
        dataHelper = new MovieDataHelper();
        movieList = dataHelper.getMovieList();
    }

    //region addMovieToNewcomersCache
    @Test
    public void addMovieToNewcomersCacheTest(){
        Movie movie = dataHelper.createRandomMovie(6);
        // mocking
        Mockito.doReturn(movieList).when(cacheServiceMock).findListFromCacheWithKey(Mockito.eq(CacheKey.NEWCOMERS.getName()));
        Mockito.doNothing().when(cacheServiceMock).putCache(Mockito.eq(CacheKey.NEWCOMERS.getName()), Mockito.any(List.class));
        // actual method call
        messageManagerService.addMovieToNewcomersCache(movie);
        // assertions
        Mockito.verify(cacheServiceMock, Mockito.times(1)).putCache(Mockito.eq(CacheKey.NEWCOMERS.getName()), Mockito.any(List.class));
    }

    @Test
    public void addMovieToNewcomersCacheWhenCacheIsNullTest(){
        Movie movie = dataHelper.createRandomMovie(6);
        // mocking
        Mockito.when(cacheServiceMock.findListFromCacheWithKey(Mockito.eq(CacheKey.NEWCOMERS.getName()))).thenReturn(null);
        Mockito.doNothing().when(cacheServiceMock).putCache(Mockito.eq(CacheKey.NEWCOMERS.getName()), Mockito.any(List.class));
        // actual method call
        messageManagerService.addMovieToNewcomersCache(movie);
        // assertions
        Mockito.verify(cacheServiceMock, Mockito.times(1)).putCache(Mockito.eq(CacheKey.NEWCOMERS.getName()), Mockito.any(List.class));
    }
    //endregion addMovieToNewcomersCache
}
