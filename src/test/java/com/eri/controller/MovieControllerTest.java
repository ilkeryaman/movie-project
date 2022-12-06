package com.eri.controller;

import com.eri.base.MovieProjectJUnitTestBase;
import com.eri.helper.MovieDataHelper;
import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
import com.eri.service.impl.MovieManagerDBServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MovieControllerTest extends MovieProjectJUnitTestBase {

    @Resource
    MovieDataHelper dataHelper;

    @Resource
    MovieController movieController;

    @MockBean(MovieManagerDBServiceImpl.class)
    IMovieManagerService movieManagerService;

    @Resource
    private ObjectMapper objectMapper;


    //region listMovies
    @Test
    public void listMoviesWithoutIdTest(){
        boolean fromCache = false;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        Mockito.when(movieManagerService.getMovies(fromCache)).thenReturn(moviesExpected);
        List<Movie> listMovieResponse = movieController.listMovies(null, fromCache);
        Assert.assertEquals(moviesExpected, listMovieResponse);
    }

    @Test
    public void listMoviesWithoutIdFromCacheTest(){
        boolean fromCache = true;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        Mockito.when(movieManagerService.getMovies(fromCache)).thenReturn(moviesExpected);
        List<Movie> listMovieResponse = movieController.listMovies(null, fromCache);
        Assert.assertEquals(moviesExpected, listMovieResponse);
    }

    @Test
    public void listMoviesWithIdTest(){
        Movie movie = dataHelper.getExpectedMovie();
        List<Movie> moviesExpected = Arrays.asList(movie);
        Mockito.when(movieManagerService.findMovieById(Mockito.anyInt())).thenReturn(movie);
        List<Movie> listMovieResponse = movieController.listMovies(1, false);
        Assert.assertEquals(moviesExpected, listMovieResponse);
    }

    @Test
    public void listMoviesWithIdNullTest(){
        List<Movie> moviesExpected = Collections.emptyList();
        Mockito.when(movieManagerService.findMovieById(Mockito.anyInt())).thenReturn(null);
        List<Movie> listMovieResponse = movieController.listMovies(1, false);
        Assert.assertEquals(moviesExpected, listMovieResponse);
    }
    //endregion listMovies


    //region getMovies
    @Test
    public void getMoviesTest(){
        Movie movieExpected = dataHelper.getExpectedMovie();
        Mockito.when(movieManagerService.findMovieById(Mockito.anyInt())).thenReturn(movieExpected);
        Movie getMovieResponse = movieController.getMovie(1);
        Assert.assertEquals(movieExpected, getMovieResponse);
    }
    //endregion getMovies

    //region addMovie
    @Test
    public void addMovieTest() {
        Mockito.doNothing().when(movieManagerService).addMovie(Mockito.any(Movie.class));
        movieController.addMovie(dataHelper.getExpectedMovie());
    }
    //endregion addMovie

    //region addMovie
    @Test
    public void deleteMovieTest() {
        Mockito.doNothing().when(movieManagerService).removeMovieById(Mockito.anyInt());
        movieController.deleteMovie(1);
    }
    //endregion addMovie

}
