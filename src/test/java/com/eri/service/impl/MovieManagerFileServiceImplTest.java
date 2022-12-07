package com.eri.service.impl;

import com.eri.base.MovieProjectJUnitTestBase;
import com.eri.constant.enums.CacheKey;
import com.eri.dal.service.IMovieService;
import com.eri.exception.CacheNotInitializedException;
import com.eri.exception.MovieNotFoundException;
import com.eri.helper.MovieDataHelper;
import com.eri.model.Movie;
import com.eri.service.MovieManagerService;
import com.eri.service.cache.ICacheService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class MovieManagerFileServiceImplTest extends MovieProjectJUnitTestBase {
    @Resource
    MovieDataHelper dataHelper;

    @SpyBean(name = "movieManagerFileService")
    MovieManagerService movieManagerService;

    @MockBean
    IMovieService movieService;

    @MockBean
    ICacheService cacheService;

    //region getMovies
    @Test
    public void getMoviesTest(){
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(false);
        ReflectionTestUtils.setField(movieManagerService, "movies", moviesExpected);
        List<Movie> moviesReturned = movieManagerService.getMovies();
        Assert.assertEquals(moviesExpected, moviesReturned);
    }

    @Test
    public void getMoviesWithCacheFalseTest(){
        boolean fromCache = false;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        ReflectionTestUtils.setField(movieManagerService, "movies", moviesExpected);
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
    public void getMoviesWithNullCacheTest() {
        boolean fromCache = true;
        Mockito.when(cacheService.findListFromCacheWithKey(CacheKey.MOVIES.getName())).thenReturn(null);
        movieManagerService.getMovies(fromCache);
    }
    //endregion getMovies

    //region findMovieById
    @Test
    public void findMovieByIdTest(){
        int id = 1;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(false);
        Movie movieExpected = moviesExpected.stream().filter(movie -> movie.getId() == id).findFirst().get();
        Mockito.when(movieManagerService.getMovies()).thenReturn(moviesExpected);
        Movie foundMovie = movieManagerService.findMovieById(id);
        Assert.assertEquals(movieExpected, foundMovie);
    }

    @Test(expected = MovieNotFoundException.class)
    public void findMovieByIdNullMovieTest(){
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(false);
        Mockito.when(movieManagerService.getMovies()).thenReturn(moviesExpected);
        movieManagerService.findMovieById(1000);
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
        int id = 1;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(false);
        Optional<Movie> movieOptional = moviesExpected.stream().filter(movie -> movie.getId() == id).findFirst();
        Assert.assertTrue(movieOptional.isPresent());
        Mockito.when(movieManagerService.getMovies()).thenReturn(moviesExpected);
        movieManagerService.removeMovieById(id);
        movieOptional = moviesExpected.stream().filter(movie -> movie.getId() == id).findFirst();
        Assert.assertFalse(movieOptional.isPresent());
    }
    //endregion removeMovieById
}
