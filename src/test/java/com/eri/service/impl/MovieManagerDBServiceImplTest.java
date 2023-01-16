package com.eri.service.impl;

import com.eri.constant.enums.CacheKey;
import com.eri.converter.mapstruct.MovieEntityToMovieMapper;
import com.eri.converter.mapstruct.MovieToMovieEntityMapper;
import com.eri.dal.entity.MovieEntity;
import com.eri.dal.service.IMovieService;
import com.eri.exception.CacheNotInitializedException;
import com.eri.helper.MovieDataHelper;
import com.eri.helper.MovieEntityDataHelper;
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

import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MovieManagerDBServiceImplTest {
    @InjectMocks
    private MovieManagerDBServiceImpl movieManagerDBService;

    //region mocks
    @Mock
    private MovieEntityToMovieMapper movieEntityToMovieMapperMock;

    @Mock
    private MovieToMovieEntityMapper movieToMovieEntityMapperMock;

    @Mock
    private IMovieService movieServiceMock;

    @Mock
    private ICacheService cacheServiceMock;

    @Mock
    private IMessageService messageServiceMock;
    //endregion mocks

    //region fields
    private MovieDataHelper dataHelper;
    private MovieEntityDataHelper entityDataHelper;
    private List<MovieEntity> movieEntityList;
    private List<Movie> movieList;
    //endregion fields


    @Before
    public void init(){
        dataHelper = new MovieDataHelper();
        entityDataHelper = new MovieEntityDataHelper();
        movieEntityList = entityDataHelper.getMovieList();
        movieList = dataHelper.getMovieList();
    }


    //region getMovies
    @Test
    public void getMoviesTest(){
        boolean fromCache = false;
        // mocking
        Mockito.when(movieServiceMock.getMovieList()).thenReturn(movieEntityList);
        Mockito.when(movieEntityToMovieMapperMock.mapMovieEntityToMovie(Mockito.eq(movieEntityList.get(0)))).thenReturn(movieList.get(0));
        Mockito.when(movieEntityToMovieMapperMock.mapMovieEntityToMovie(Mockito.eq(movieEntityList.get(1)))).thenReturn(movieList.get(1));
        Mockito.when(cacheServiceMock.findListFromCacheWithKey(Mockito.eq(CacheKey.MOVIES.getName()))).thenReturn(null);
        Mockito.doNothing().when(cacheServiceMock).putCache(Mockito.eq(CacheKey.MOVIES.getName()), Mockito.any(List.class));
        // actual method call
        List<Movie> moviesActual = movieManagerDBService.getMovies(fromCache);
        // assertions
        Assert.assertTrue(movieList.size() == moviesActual.size());
        Assert.assertEquals(movieList.get(0).getId(), moviesActual.get(0).getId());
        Assert.assertEquals(movieList.get(0).getTitle(), moviesActual.get(0).getTitle());
        Assert.assertTrue(movieList.get(0).getCategories().size() == moviesActual.get(0).getCategories().size());
        Assert.assertEquals(movieList.get(0).getCategories().get(0), moviesActual.get(0).getCategories().get(0));
        Assert.assertEquals(movieList.get(0).getCategories().get(1), moviesActual.get(0).getCategories().get(1));
        Assert.assertTrue(movieList.get(0).getDirectors().size() == moviesActual.get(0).getDirectors().size());
        Assert.assertEquals(movieList.get(0).getDirectors().get(0).getName(), moviesActual.get(0).getDirectors().get(0).getName());
        Assert.assertEquals(movieList.get(0).getDirectors().get(0).getSurname(), moviesActual.get(0).getDirectors().get(0).getSurname());
        Assert.assertTrue(movieList.get(0).getStars().size() == moviesActual.get(0).getStars().size());
        Assert.assertEquals(movieList.get(0).getStars().get(0).getName(), moviesActual.get(0).getStars().get(0).getName());
        Assert.assertEquals(movieList.get(0).getStars().get(0).getSurname(), moviesActual.get(0).getStars().get(0).getSurname());
        Assert.assertEquals(movieList.get(1).getId(), moviesActual.get(1).getId());
        Assert.assertEquals(movieList.get(1).getTitle(), moviesActual.get(1).getTitle());
        Assert.assertTrue(movieList.get(1).getCategories().size() == moviesActual.get(1).getCategories().size());
        Assert.assertEquals(movieList.get(1).getCategories().get(0), moviesActual.get(1).getCategories().get(0));
        Assert.assertEquals(movieList.get(1).getCategories().get(1), moviesActual.get(1).getCategories().get(1));
        Assert.assertEquals(movieList.get(1).getCategories().get(2), moviesActual.get(1).getCategories().get(2));
        Assert.assertTrue(movieList.get(1).getDirectors().size() == moviesActual.get(1).getDirectors().size());
        Assert.assertEquals(movieList.get(1).getDirectors().get(0).getName(), moviesActual.get(1).getDirectors().get(0).getName());
        Assert.assertEquals(movieList.get(1).getDirectors().get(0).getSurname(), moviesActual.get(1).getDirectors().get(0).getSurname());
        Assert.assertTrue(movieList.get(1).getStars().size() == moviesActual.get(1).getStars().size());
        Assert.assertEquals(movieList.get(1).getStars().get(0).getName(), moviesActual.get(1).getStars().get(0).getName());
        Assert.assertEquals(movieList.get(1).getStars().get(0).getSurname(), moviesActual.get(1).getStars().get(0).getSurname());
    }

    @Test
    public void getMoviesMovieEntityNullTest(){
        boolean fromCache = false;
        // mocking
        Mockito.when(movieServiceMock.getMovieList()).thenReturn(null);
        Mockito.when(cacheServiceMock.findListFromCacheWithKey(Mockito.eq(CacheKey.MOVIES.getName()))).thenReturn(null);
        // actual method call
        List<Movie> moviesActual = movieManagerDBService.getMovies(fromCache);
        // assertions
        Assert.assertEquals(Collections.emptyList(), moviesActual);
    }

    @Test
    public void getMoviesFromCacheTest(){
        boolean fromCache = true;
        // mocking
        Mockito.doReturn(movieList).when(cacheServiceMock).findListFromCacheWithKey(Mockito.eq(CacheKey.MOVIES.getName()));
        // actual method call
        List<Movie> moviesActual = movieManagerDBService.getMovies(fromCache);
        // assertions
        Assert.assertEquals(movieList, moviesActual);
    }

    @Test(expected = CacheNotInitializedException.class)
    public void getMoviesTestFromNullCache(){
        boolean fromCache = true;
        // mocking
        Mockito.when(cacheServiceMock.findListFromCacheWithKey(CacheKey.MOVIES.getName())).thenReturn(null);
        // actual method call
        movieManagerDBService.getMovies(fromCache);
    }
    //endregion getMovies

    //region listNewcomers
    @Test
    public void listNewComersTest(){
        // mocking
        Mockito.doReturn(movieList).when(cacheServiceMock).findListFromCacheWithKey(Mockito.eq(CacheKey.NEWCOMERS.getName()));
        // actual method call
        List<Movie> moviesActual = movieManagerDBService.listNewComers();
        // assertions
        Assert.assertEquals(movieList, moviesActual);
    }

    @Test
    public void listNewComersNullCacheTest(){
        // mocking
        Mockito.when(cacheServiceMock.findListFromCacheWithKey(Mockito.eq(CacheKey.NEWCOMERS.getName()))).thenReturn(null);
        // actual method call
        List<Movie> moviesActual = movieManagerDBService.listNewComers();
        // assertions
        Assert.assertTrue(moviesActual.isEmpty());
    }
    //endregion listNewComers

    //region findMovieById
    @Test
    public void findMovieByIdTest(){
        MovieEntity movieEntity = movieEntityList.get(0);
        Movie movie = movieList.get(0);
        // mocking
        Mockito.when(movieServiceMock.getMovieById(Mockito.anyLong())).thenReturn(movieEntity);
        Mockito.when(movieEntityToMovieMapperMock.mapMovieEntityToMovie(Mockito.eq(movieEntity))).thenReturn(movie);
        // actual method call
        Movie movieActual = movieManagerDBService.findMovieById(1);
        // assertions
        Assert.assertEquals(movie.getId(), movieActual.getId());
        Assert.assertEquals(movie.getTitle(), movieActual.getTitle());
        Assert.assertTrue(movie.getCategories().size() == movieActual.getCategories().size());
        Assert.assertEquals(movie.getCategories().get(0), movieActual.getCategories().get(0));
        Assert.assertEquals(movie.getCategories().get(1), movieActual.getCategories().get(1));
        Assert.assertTrue(movie.getDirectors().size() == movieActual.getDirectors().size());
        Assert.assertEquals(movie.getDirectors().get(0).getName(), movieActual.getDirectors().get(0).getName());
        Assert.assertEquals(movie.getDirectors().get(0).getSurname(), movieActual.getDirectors().get(0).getSurname());
        Assert.assertTrue(movie.getStars().size() == movieActual.getStars().size());
        Assert.assertEquals(movie.getStars().get(0).getName(), movieActual.getStars().get(0).getName());
        Assert.assertEquals(movie.getStars().get(0).getSurname(), movieActual.getStars().get(0).getSurname());
    }

    @Test
    public void findMovieByIdMovieEntityNullTest(){
        // mocking
        Mockito.when(movieServiceMock.getMovieById(Mockito.anyLong())).thenReturn(null);
        // actual method call
        Movie movieActual = movieManagerDBService.findMovieById(1200);
        // assertions
        Assert.assertNull(movieActual);
    }
    //endregion findMovieById

    //region addMovie
    @Test
    public void addMovieTest(){
        int id = 3;
        MovieEntity movieEntity = entityDataHelper.createRandomMovieEntity(id);
        Movie movie = dataHelper.createRandomMovie(id);
        // mocking
        Mockito.when(movieToMovieEntityMapperMock.mapMovieToMovieEntity(Mockito.eq(movie))).thenReturn(movieEntity);
        Mockito.when(movieServiceMock.saveMovie(Mockito.eq(movieEntity))).thenReturn(movieEntity);
        Mockito.doNothing().when(messageServiceMock).sendMessage(Mockito.any(EventMessage.class));
        // actual method call
        movieManagerDBService.addMovie(movie);
    }
    //endregion addMovie

    //region removeMovieById
    @Test
    public void deleteMovieTest(){
        // mocking
        Mockito.doNothing().when(movieServiceMock).deleteMovie(Mockito.anyLong());
        // actual method call
        movieManagerDBService.removeMovieById(1);
    }
    //endregion removeMovieById

}
