package com.eri.util;

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
public class CacheUtilTest {
    @InjectMocks
    CacheUtil cacheUtil;

    //region mocks
    @Mock
    ICacheService cacheService;
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

    //region cacheIfNeeded
    @Test
    public void cacheIfNeededTest(){
        // mocking
        Mockito.doReturn(movieList).when(cacheService).findListFromCacheWithKey(Mockito.eq(CacheKey.MOVIES.getName()));
        // actual method call
        cacheUtil.cacheIfNeeded(cacheService, movieList);
    }

    @Test
    public void cacheIfNeededTestCacheNull(){
        // mocking
        Mockito.when(cacheService.findListFromCacheWithKey(Mockito.eq(CacheKey.MOVIES.getName()))).thenReturn(null);
        Mockito.doNothing().when(cacheService).putCache(Mockito.eq(CacheKey.MOVIES.getName()), Mockito.any(List.class));
        // actual method call
        cacheUtil.cacheIfNeeded(cacheService, movieList);
    }
    //endregion cacheIfNeeded
}
