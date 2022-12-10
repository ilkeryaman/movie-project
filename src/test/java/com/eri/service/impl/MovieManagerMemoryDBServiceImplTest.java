package com.eri.service.impl;

import com.eri.base.MovieProjectJUnitTestBase;
import com.eri.constant.enums.CacheKey;
import com.eri.exception.CacheNotInitializedException;
import com.eri.exception.MovieNotFoundException;
import com.eri.helper.MovieDataHelper;
import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
import com.eri.service.cache.ICacheService;
import com.eri.service.memorydb.IMovieMemoryDBService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.util.List;

public class MovieManagerMemoryDBServiceImplTest extends MovieProjectJUnitTestBase {
    @Resource(name = "movieManagerMemoryDBService")
    private IMovieManagerService movieManagerService;

    @Resource
    private MovieDataHelper dataHelper;

    @MockBean
    private IMovieMemoryDBService movieMemoryDBService;

    @MockBean
    private ICacheService cacheService;

    //region getMovies
    @Test
    public void getMoviesTest(){
        boolean fromCache = false;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        Mockito.when(movieMemoryDBService.getMovies()).thenReturn(moviesExpected);
        List<Movie> moviesReturned = movieManagerService.getMovies(fromCache);
        Assert.assertEquals(moviesExpected, moviesReturned);
    }

    @Test
    public void getMoviesWithCacheTest(){
        boolean fromCache = true;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        Mockito.doReturn(moviesExpected).when(cacheService).findListFromCacheWithKey(CacheKey.MOVIES.getName());
        List<Movie> moviesReturned = movieManagerService.getMovies(fromCache);
        Assert.assertEquals(moviesExpected, moviesReturned);
    }

    @Test(expected = CacheNotInitializedException.class)
    public void getMoviesWithNullCacheTest(){
        boolean fromCache = true;
        Mockito.when(cacheService.findListFromCacheWithKey(CacheKey.MOVIES.getName())).thenReturn(null);
        movieManagerService.getMovies(fromCache);
    }
    //endregion getMovies

    //region findMovieById
    @Test
    public void findMovieByIdTest(){
        boolean fromCache = false;
        int id = 1;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        Movie movieExpected = moviesExpected.stream().filter(movie -> movie.getId() == id).findFirst().get();
        Mockito.when(movieMemoryDBService.getMovies()).thenReturn(moviesExpected);
        Movie movieFound = movieManagerService.findMovieById(id);
        Assert.assertEquals(movieExpected, movieFound);
    }

    @Test(expected = MovieNotFoundException.class)
    public void findMovieByIdMovieNotFoundTest(){
        boolean fromCache = false;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        Mockito.when(movieMemoryDBService.getMovies()).thenReturn(moviesExpected);
        movieManagerService.findMovieById(1200);
    }
    //endregion findMovieById

    //region addMovie
    @Test
    public void addMovieTest(){
        boolean fromCache = false;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        int initialCount = moviesExpected.size();
        Mockito.when(movieMemoryDBService.getMovies()).thenReturn(moviesExpected);
        movieManagerService.addMovie(dataHelper.getExpectedMovie());
        Assert.assertEquals(initialCount + 1, moviesExpected.size());
    }
    //endregion addMovie

    //region removeMovieById
    @Test
    public void deleteMovieTest(){
        boolean fromCache = false;
        int id = 1;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        Assert.assertTrue(moviesExpected.stream().filter((movie -> movie.getId() == id)).findFirst().isPresent());
        Mockito.when(movieMemoryDBService.getMovies()).thenReturn(moviesExpected);
        movieManagerService.removeMovieById(id);
        Assert.assertFalse(moviesExpected.stream().filter((movie -> movie.getId() == id)).findFirst().isPresent());
    }
    //endregion removeMovieById
}
