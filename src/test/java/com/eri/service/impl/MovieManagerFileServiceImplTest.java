package com.eri.service.impl;

import com.eri.constant.enums.CacheKey;
import com.eri.exception.CacheNotInitializedException;
import com.eri.exception.MovieNotFoundException;
import com.eri.helper.MovieDataHelper;
import com.eri.model.Movie;
import com.eri.model.messaging.EventMessage;
import com.eri.service.cache.ICacheService;
import com.eri.service.messaging.IMessageService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MovieManagerFileServiceImplTest {
    @InjectMocks
    private MovieManagerFileServiceImpl movieManagerFileService = Mockito.spy(new MovieManagerFileServiceImpl());

    //region mocks
    @Mock
    private ICacheService cacheServiceMock;

    @Mock
    private IMessageService messageServiceMock;
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

    //region getMovies
    @Test
    public void getMoviesTest(){
        ReflectionTestUtils.setField(movieManagerFileService, "movies", movieList);
        // actual method call
        List<Movie> moviesActual = movieManagerFileService.getMovies();
        // assertions
        Assert.assertEquals(movieList, moviesActual);
    }

    @Test
    public void getMoviesWithCacheFalseTest(){
        boolean fromCache = false;
        ReflectionTestUtils.setField(movieManagerFileService, "movies", movieList);
        // actual method call
        List<Movie> moviesActual = movieManagerFileService.getMovies(fromCache);
        // assertions
        Assert.assertEquals(movieList, moviesActual);
    }

    @Test
    public void getMoviesWithCacheTest(){
        boolean fromCache = true;
        // mocking
        Mockito.doReturn(movieList).when(cacheServiceMock).findListFromCacheWithKey(Mockito.eq(CacheKey.MOVIES.getName()));
        // actual method call
        List<Movie> moviesActual = movieManagerFileService.getMovies(fromCache);
        // assertions
        Assert.assertEquals(movieList, moviesActual);
    }

    @Test(expected = CacheNotInitializedException.class)
    public void getMoviesWithNullCacheTest() {
        boolean fromCache = true;
        // mocking
        Mockito.when(cacheServiceMock.findListFromCacheWithKey(CacheKey.MOVIES.getName())).thenReturn(null);
        // actual method call
        movieManagerFileService.getMovies(fromCache);
    }
    //endregion getMovies

    //region listNewcomers
    @Test
    public void listNewComersTest(){
        // mocking
        Mockito.doReturn(movieList).when(cacheServiceMock).findListFromCacheWithKey(Mockito.eq(CacheKey.NEWCOMERS.getName()));
        // actual method call
        List<Movie> moviesActual = movieManagerFileService.listNewComers();
        // assertions
        Assert.assertEquals(movieList, moviesActual);
    }

    @Test
    public void listNewComersNullCacheTest(){
        // mocking
        Mockito.when(cacheServiceMock.findListFromCacheWithKey(Mockito.eq(CacheKey.NEWCOMERS.getName()))).thenReturn(null);
        // actual method call
        List<Movie> moviesActual = movieManagerFileService.listNewComers();
        // assertions
        Assert.assertTrue(moviesActual.isEmpty());
    }
    //endregion listNewComers

    //region findMovieById
    @Test
    public void findMovieByIdTest(){
        int id = 1;
        // mocking
        Mockito.when(movieManagerFileService.getMovies()).thenReturn(movieList);
        // actual method call
        Movie movieActual = movieManagerFileService.findMovieById(id);
        // assertion
        Assert.assertEquals(movieList.get(0), movieActual);
    }

    @Test(expected = MovieNotFoundException.class)
    public void findMovieByIdNullMovieTest(){
        // mocking
        Mockito.when(movieManagerFileService.getMovies()).thenReturn(movieList);
        // actual method call
        movieManagerFileService.findMovieById(1200);
    }
    //endregion findMovieById

    //region addMovie
    @Test
    public void addMovieTest(){
        int initialMovieCount = movieList.size();
        Movie movie = dataHelper.createRandomMovie(3);
        // mocking
        Mockito.when(movieManagerFileService.getMovies()).thenReturn(movieList);
        Mockito.doNothing().when(messageServiceMock).sendMessage(Mockito.any(EventMessage.class));
        // actual method call
        movieManagerFileService.addMovie(movie);
        // assertions
        Assert.assertEquals(initialMovieCount + 1, movieList.size());
        Assert.assertEquals(movieList.get(2).getId(), movie.getId());
        Assert.assertEquals(movieList.get(2).getTitle(), movie.getTitle());
        Assert.assertEquals(movieList.get(2).getCategories().size(), movie.getCategories().size());
        Assert.assertEquals(movieList.get(2).getCategories().get(0), movie.getCategories().get(0));
        Assert.assertEquals(movieList.get(2).getCategories().get(1), movie.getCategories().get(1));
        Assert.assertEquals(movieList.get(2).getDirectors().size(), movie.getDirectors().size());
        Assert.assertEquals(movieList.get(2).getDirectors().get(0).getName(), movie.getDirectors().get(0).getName());
        Assert.assertEquals(movieList.get(2).getDirectors().get(0).getSurname(), movie.getDirectors().get(0).getSurname());
        Assert.assertEquals(movieList.get(2).getStars().size(), movie.getStars().size());
        Assert.assertEquals(movieList.get(2).getStars().get(0).getName(), movie.getStars().get(0).getName());
        Assert.assertEquals(movieList.get(2).getStars().get(0).getSurname(), movie.getStars().get(0).getSurname());
    }
    //endregion addMovie

    //region removeMovieById
    @Test
    public void removeMovieByIdTest(){
        int id = 1;
        // mocking
        Mockito.when(movieManagerFileService.getMovies()).thenReturn(movieList);
        // actual method call
        movieManagerFileService.removeMovieById(id);
        // assertion
        Assert.assertFalse(movieList.stream().filter(movie -> movie.getId() == id).findAny().isPresent());
    }
    //endregion removeMovieById
}
