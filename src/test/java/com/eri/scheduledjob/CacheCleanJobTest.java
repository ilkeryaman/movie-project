package com.eri.scheduledjob;

import com.eri.constant.enums.CacheKey;
import com.eri.helper.MovieDataHelper;
import com.eri.service.cache.ICacheService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CacheCleanJobTest {

    @InjectMocks
    CacheCleanJob cacheCleanJob;

    //region mocks
    @Mock
    ICacheService cacheService;
    //endregion mocks

    //region fields
    private MovieDataHelper dataHelper;
    //endregion fields

    @Before
    public void init(){
        dataHelper = new MovieDataHelper();
    }

    //region cleanCache
    @Test
    public void cleanCacheTest() {
        String key = CacheKey.MOVIES.getName();
        Mockito.doReturn(dataHelper.getMovieList()).when(cacheService).findListFromCacheWithKey(Mockito.eq(key));
        Mockito.doNothing().when(cacheService).evictCache(Mockito.eq(key));
        cacheCleanJob.cleanCache();
        Mockito.verify(cacheService, Mockito.atLeastOnce()).evictCache(Mockito.eq(key));
    }
    //endregion cleanCache
}
