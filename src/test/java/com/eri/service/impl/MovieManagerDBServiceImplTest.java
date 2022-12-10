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
    private IMovieService movieService;

    @MockBean
    private ICacheService cacheService;

    //region getMovies
    @Test
    public void getMoviesTest(){
        boolean fromCache = false;
        List<MovieEntity> movieEntityListExpected = entityDataHelper.getExpectedMovieEntityList();
        List<Movie> moviesExpected = new ArrayList<>();
        movieEntityListExpected.forEach(movieEntity -> moviesExpected.add(movieEntityToMovieMapper.mapMovieEntityToMovie(movieEntity)));
        Mockito.when(movieService.getMovieList()).thenReturn(movieEntityListExpected);
        List<Movie> listMovieResponse = movieManagerService.getMovies(fromCache);
        Assert.assertTrue(moviesExpected.size() == listMovieResponse.size());
        Assert.assertEquals(moviesExpected.get(0).getId(), listMovieResponse.get(0).getId());
        Assert.assertEquals(moviesExpected.get(0).getTitle(), listMovieResponse.get(0).getTitle());
        Assert.assertTrue(moviesExpected.get(0).getCategories().size() == listMovieResponse.get(0).getCategories().size());
        Assert.assertEquals(moviesExpected.get(0).getCategories().get(0), listMovieResponse.get(0).getCategories().get(0));
        Assert.assertTrue(moviesExpected.get(0).getDirectors().size() == listMovieResponse.get(0).getDirectors().size());
        Assert.assertEquals(moviesExpected.get(0).getDirectors().get(0).getName(), listMovieResponse.get(0).getDirectors().get(0).getName());
        Assert.assertEquals(moviesExpected.get(0).getDirectors().get(0).getSurname(), listMovieResponse.get(0).getDirectors().get(0).getSurname());
        Assert.assertTrue(moviesExpected.get(0).getStars().size() == listMovieResponse.get(0).getStars().size());
        Assert.assertEquals(moviesExpected.get(0).getStars().get(0).getName(), listMovieResponse.get(0).getStars().get(0).getName());
        Assert.assertEquals(moviesExpected.get(0).getStars().get(0).getSurname(), listMovieResponse.get(0).getStars().get(0).getSurname());
    }

    @Test
    public void getMoviesMovieEntityNullTest(){
        boolean fromCache = false;
        Mockito.when(movieService.getMovieList()).thenReturn(null);
        List<Movie> listMovieResponse = movieManagerService.getMovies(fromCache);
        Assert.assertEquals(Collections.emptyList(), listMovieResponse);
    }

    @Test
    public void getMoviesFromCacheTest(){
        boolean fromCache = true;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        Mockito.doReturn(moviesExpected).when(cacheService).findListFromCacheWithKey(CacheKey.MOVIES.getName());
        List<Movie> listMovieResponse = movieManagerService.getMovies(fromCache);
        Assert.assertEquals(moviesExpected, listMovieResponse);
    }

    @Test(expected = CacheNotInitializedException.class)
    public void getMoviesTestFromNullCache(){
        boolean fromCache = true;
        Mockito.when(cacheService.findListFromCacheWithKey(CacheKey.MOVIES.getName())).thenReturn(null);
        movieManagerService.getMovies(fromCache);
    }
    //endregion

    //region findMovieById
    @Test
    public void findMovieByIdTest(){
        MovieEntity movieEntityExpected = entityDataHelper.getExpectedMovieEntity();
        Movie movieExpected = movieEntityToMovieMapper.mapMovieEntityToMovie(movieEntityExpected);
        Mockito.when(movieService.getMovieById(Mockito.anyLong())).thenReturn(movieEntityExpected);
        Movie movieFound = movieManagerService.findMovieById(1);
        Assert.assertEquals(movieExpected.getId(), movieFound.getId());
        Assert.assertEquals(movieExpected.getTitle(), movieFound.getTitle());
        Assert.assertTrue(movieExpected.getCategories().size() == movieFound.getCategories().size());
        Assert.assertEquals(movieExpected.getCategories().get(0), movieFound.getCategories().get(0));
        Assert.assertTrue(movieExpected.getDirectors().size() == movieFound.getDirectors().size());
        Assert.assertEquals(movieExpected.getDirectors().get(0).getName(), movieFound.getDirectors().get(0).getName());
        Assert.assertEquals(movieExpected.getDirectors().get(0).getSurname(), movieFound.getDirectors().get(0).getSurname());
        Assert.assertTrue(movieExpected.getStars().size() == movieFound.getStars().size());
        Assert.assertEquals(movieExpected.getStars().get(0).getName(), movieFound.getStars().get(0).getName());
        Assert.assertEquals(movieExpected.getStars().get(0).getSurname(), movieFound.getStars().get(0).getSurname());
    }

    @Test
    public void findMovieByIdMovieEntityNullTest(){
        Mockito.when(movieService.getMovieById(Mockito.anyLong())).thenReturn(null);
        Movie movieFound = movieManagerService.findMovieById(1);
        Assert.assertNull(movieFound);
    }
    //endregion findMovieById

    //region addMovie
    @Test
    public void addMovieTest(){
        Mockito.when(movieService.saveMovie(Mockito.any(MovieEntity.class))).thenReturn(null);
        movieManagerService.addMovie(dataHelper.getExpectedMovie());
    }
    //endregion addMovie

    //region removeMovieById
    @Test
    public void deleteMovieTest(){
        Mockito.doNothing().when(movieService).deleteMovie(Mockito.anyLong());
        movieManagerService.removeMovieById(1);
    }
    //endregion removeMovieById
}
