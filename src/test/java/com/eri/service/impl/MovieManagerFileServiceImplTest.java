package com.eri.service.impl;

import com.eri.base.MovieProjectJUnitTestBase;
import com.eri.constant.enums.CacheKey;
import com.eri.exception.CacheNotInitializedException;
import com.eri.exception.MovieNotFoundException;
import com.eri.helper.MovieDataHelper;
import com.eri.model.Movie;
import com.eri.service.MovieManagerService;
import com.eri.service.cache.ICacheService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class MovieManagerFileServiceImplTest extends MovieProjectJUnitTestBase {
    @Resource
    private MovieDataHelper dataHelper;

    @SpyBean(name = "movieManagerFileService")
    private MovieManagerService movieManagerService;

    @MockBean
    private ICacheService cacheServiceMock;

    //region getMovies
    @Test
    public void getMoviesTest(){
        boolean fromCache = false;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        ReflectionTestUtils.setField(movieManagerService, "movies", moviesExpected);
        List<Movie> moviesActual = movieManagerService.getMovies();
        Assert.assertEquals(moviesExpected, moviesActual);
    }

    @Test
    public void getMoviesWithCacheFalseTest(){
        boolean fromCache = false;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        ReflectionTestUtils.setField(movieManagerService, "movies", moviesExpected);
        List<Movie> moviesActual = movieManagerService.getMovies(fromCache);
        Assert.assertEquals(moviesExpected, moviesActual);
    }

    @Test
    public void getMoviesWithCacheTest(){
        boolean fromCache = true;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        Mockito.doReturn(moviesExpected).when(cacheServiceMock).findListFromCacheWithKey(CacheKey.MOVIES.getName());
        List<Movie> moviesActual = movieManagerService.getMovies(fromCache);
        Assert.assertEquals(moviesExpected, moviesActual);
    }

    @Test(expected = CacheNotInitializedException.class)
    public void getMoviesWithNullCacheTest() {
        boolean fromCache = true;
        Mockito.when(cacheServiceMock.findListFromCacheWithKey(CacheKey.MOVIES.getName())).thenReturn(null);
        movieManagerService.getMovies(fromCache);
    }
    //endregion getMovies

    //region findMovieById
    @Test
    public void findMovieByIdTest(){
        boolean fromCache = false;
        int id = 1;
        List<Movie> moviesList = dataHelper.getExpectedMovieList(fromCache);
        Movie movieExpected = moviesList.stream().filter(movie -> movie.getId() == id).findFirst().get();
        Mockito.when(movieManagerService.getMovies()).thenReturn(moviesList);
        Movie movieActual = movieManagerService.findMovieById(id);
        Assert.assertEquals(movieExpected, movieActual);
    }

    @Test(expected = MovieNotFoundException.class)
    public void findMovieByIdNullMovieTest(){
        boolean fromCache = false;
        List<Movie> moviesList = dataHelper.getExpectedMovieList(fromCache);
        Mockito.when(movieManagerService.getMovies()).thenReturn(moviesList);
        movieManagerService.findMovieById(1200);
    }
    //endregion findMovieById

    //region addMovie
    @Test
    public void addMovieTest(){
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(false);
        Movie movie = dataHelper.getExpectedMovie();
        int initialCount = moviesExpected.size();
        Mockito.when(movieManagerService.getMovies()).thenReturn(moviesExpected);
        movieManagerService.addMovie(movie);
        Assert.assertEquals(initialCount + 1, moviesExpected.size());
    }
    //endregion addMovie

    //region removeMovieById
    @Test
    public void removeMovieByIdTest(){
        boolean fromCache = false;
        int id = 1;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        Optional<Movie> movieOptional = moviesExpected.stream().filter(movie -> movie.getId() == id).findFirst();
        Assert.assertTrue(movieOptional.isPresent());
        Mockito.when(movieManagerService.getMovies()).thenReturn(moviesExpected);
        movieManagerService.removeMovieById(id);
        movieOptional = moviesExpected.stream().filter(movie -> movie.getId() == id).findFirst();
        Assert.assertFalse(movieOptional.isPresent());
    }
    //endregion removeMovieById
}
