package com.eri.service.impl;

import com.eri.base.MovieProjectJUnitTestBase;
import com.eri.constant.enums.CacheKey;
import com.eri.converter.mapstruct.MovieEntityToMovieMapper;
import com.eri.converter.mapstruct.MovieToMovieEntityMapper;
import com.eri.dal.entity.MovieEntity;
import com.eri.dal.service.IMovieService;
import com.eri.exception.CacheNotInitializedException;
import com.eri.helper.MovieDataHelper;
import com.eri.helper.MovieEntityDataHelper;
import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
import com.eri.service.cache.ICacheService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieManagerDBServiceImplTest extends MovieProjectJUnitTestBase {
    @Resource
    private MovieDataHelper dataHelper;

    @Resource
    private MovieEntityDataHelper entityDataHelper;

    @Resource(name = "movieManagerDBService")
    private IMovieManagerService movieManagerService;

    @Resource
    private MovieEntityToMovieMapper movieEntityToMovieMapper;

    @Resource
    private MovieToMovieEntityMapper movieToMovieEntityMapper;

    @MockBean
    private IMovieService movieServiceMock;

    @MockBean
    private ICacheService cacheServiceMock;

    //region getMovies
    @Test
    public void getMoviesTest(){
        boolean fromCache = false;
        List<MovieEntity> movieEntityListExpected = entityDataHelper.getExpectedMovieEntityList();
        List<Movie> moviesExpected = new ArrayList<>();
        movieEntityListExpected.forEach(movieEntity -> moviesExpected.add(movieEntityToMovieMapper.mapMovieEntityToMovie(movieEntity)));
        Mockito.when(movieServiceMock.getMovieList()).thenReturn(movieEntityListExpected);
        List<Movie> moviesActual = movieManagerService.getMovies(fromCache);
        Assert.assertTrue(moviesExpected.size() == moviesActual.size());
        Assert.assertEquals(moviesExpected.get(0).getId(), moviesActual.get(0).getId());
        Assert.assertEquals(moviesExpected.get(0).getTitle(), moviesActual.get(0).getTitle());
        Assert.assertTrue(moviesExpected.get(0).getCategories().size() == moviesActual.get(0).getCategories().size());
        Assert.assertEquals(moviesExpected.get(0).getCategories().get(0), moviesActual.get(0).getCategories().get(0));
        Assert.assertTrue(moviesExpected.get(0).getDirectors().size() == moviesActual.get(0).getDirectors().size());
        Assert.assertEquals(moviesExpected.get(0).getDirectors().get(0).getName(), moviesActual.get(0).getDirectors().get(0).getName());
        Assert.assertEquals(moviesExpected.get(0).getDirectors().get(0).getSurname(), moviesActual.get(0).getDirectors().get(0).getSurname());
        Assert.assertTrue(moviesExpected.get(0).getStars().size() == moviesActual.get(0).getStars().size());
        Assert.assertEquals(moviesExpected.get(0).getStars().get(0).getName(), moviesActual.get(0).getStars().get(0).getName());
        Assert.assertEquals(moviesExpected.get(0).getStars().get(0).getSurname(), moviesActual.get(0).getStars().get(0).getSurname());
    }

    @Test
    public void getMoviesMovieEntityNullTest(){
        boolean fromCache = false;
        Mockito.when(movieServiceMock.getMovieList()).thenReturn(null);
        List<Movie> moviesActual = movieManagerService.getMovies(fromCache);
        Assert.assertEquals(Collections.emptyList(), moviesActual);
    }

    @Test
    public void getMoviesFromCacheTest(){
        boolean fromCache = true;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        Mockito.doReturn(moviesExpected).when(cacheServiceMock).findListFromCacheWithKey(CacheKey.MOVIES.getName());
        List<Movie> moviesActual = movieManagerService.getMovies(fromCache);
        Assert.assertEquals(moviesExpected, moviesActual);
    }

    @Test(expected = CacheNotInitializedException.class)
    public void getMoviesTestFromNullCache(){
        boolean fromCache = true;
        Mockito.when(cacheServiceMock.findListFromCacheWithKey(CacheKey.MOVIES.getName())).thenReturn(null);
        movieManagerService.getMovies(fromCache);
    }
    //endregion

    //region findMovieById
    @Test
    public void findMovieByIdTest(){
        MovieEntity movieEntityExpected = entityDataHelper.getExpectedMovieEntity();
        Movie movieExpected = movieEntityToMovieMapper.mapMovieEntityToMovie(movieEntityExpected);
        Mockito.when(movieServiceMock.getMovieById(Mockito.anyLong())).thenReturn(movieEntityExpected);
        Movie movieActual = movieManagerService.findMovieById(1);
        Assert.assertEquals(movieExpected.getId(), movieActual.getId());
        Assert.assertEquals(movieExpected.getTitle(), movieActual.getTitle());
        Assert.assertTrue(movieExpected.getCategories().size() == movieActual.getCategories().size());
        Assert.assertEquals(movieExpected.getCategories().get(0), movieActual.getCategories().get(0));
        Assert.assertTrue(movieExpected.getDirectors().size() == movieActual.getDirectors().size());
        Assert.assertEquals(movieExpected.getDirectors().get(0).getName(), movieActual.getDirectors().get(0).getName());
        Assert.assertEquals(movieExpected.getDirectors().get(0).getSurname(), movieActual.getDirectors().get(0).getSurname());
        Assert.assertTrue(movieExpected.getStars().size() == movieActual.getStars().size());
        Assert.assertEquals(movieExpected.getStars().get(0).getName(), movieActual.getStars().get(0).getName());
        Assert.assertEquals(movieExpected.getStars().get(0).getSurname(), movieActual.getStars().get(0).getSurname());
    }

    @Test
    public void findMovieByIdMovieEntityNullTest(){
        Mockito.when(movieServiceMock.getMovieById(Mockito.anyLong())).thenReturn(null);
        Movie movieActual = movieManagerService.findMovieById(1200);
        Assert.assertNull(movieActual);
    }
    //endregion findMovieById

    //region addMovie
    @Test
    public void addMovieTest(){
        Mockito.when(movieServiceMock.saveMovie(Mockito.any(MovieEntity.class))).thenReturn(null);
        movieManagerService.addMovie(dataHelper.getExpectedMovie());
    }
    //endregion addMovie

    //region removeMovieById
    @Test
    public void deleteMovieTest(){
        Mockito.doNothing().when(movieServiceMock).deleteMovie(Mockito.anyLong());
        movieManagerService.removeMovieById(1);
    }
    //endregion removeMovieById
}
