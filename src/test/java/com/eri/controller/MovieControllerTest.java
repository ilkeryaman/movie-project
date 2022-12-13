package com.eri.controller;

import com.eri.base.MovieProjectJUnitTestBase;
import com.eri.helper.MovieDataHelper;
import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
import com.eri.service.impl.MovieManagerDBServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MovieControllerTest extends MovieProjectJUnitTestBase {

    @Resource
    MovieDataHelper dataHelper;

    @Resource
    MovieController movieController;

    @MockBean(MovieManagerDBServiceImpl.class)
    IMovieManagerService movieManagerServiceMock;

    //region listMovies
    @Test
    public void listMoviesWithoutIdTest(){
        boolean fromCache = false;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        Mockito.when(movieManagerServiceMock.getMovies(fromCache)).thenReturn(moviesExpected);
        List<Movie> moviesActual = movieController.listMovies(null, fromCache);
        Assert.assertEquals(moviesExpected, moviesActual);
    }

    @Test
    public void listMoviesWithoutIdFromCacheTest(){
        boolean fromCache = true;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        Mockito.when(movieManagerServiceMock.getMovies(fromCache)).thenReturn(moviesExpected);
        List<Movie> moviesActual = movieController.listMovies(null, fromCache);
        Assert.assertEquals(moviesExpected, moviesActual);
    }

    @Test
    public void listMoviesWithIdTest(){
        Movie movie = dataHelper.getExpectedMovie();
        List<Movie> moviesExpected = Arrays.asList(movie);
        Mockito.when(movieManagerServiceMock.findMovieById(Mockito.anyInt())).thenReturn(movie);
        List<Movie> moviesActual = movieController.listMovies(1, false);
        Assert.assertEquals(moviesExpected, moviesActual);
    }

    @Test
    public void listMoviesWithIdNullTest(){
        List<Movie> moviesExpected = Collections.emptyList();
        Mockito.when(movieManagerServiceMock.findMovieById(Mockito.anyInt())).thenReturn(null);
        List<Movie> moviesActual = movieController.listMovies(1, false);
        Assert.assertEquals(moviesExpected, moviesActual);
    }
    //endregion listMovies


    //region getMovies
    @Test
    public void getMoviesTest(){
        Movie movieExpected = dataHelper.getExpectedMovie();
        Mockito.when(movieManagerServiceMock.findMovieById(Mockito.anyInt())).thenReturn(movieExpected);
        Movie movieActual = movieController.getMovie(1);
        Assert.assertEquals(movieExpected, movieActual);
    }
    //endregion getMovies

    //region addMovie
    @Test
    public void addMovieTest() {
        Mockito.doNothing().when(movieManagerServiceMock).addMovie(Mockito.any(Movie.class));
        movieController.addMovie(dataHelper.getExpectedMovie());
    }
    //endregion addMovie

    //region addMovie
    @Test
    public void deleteMovieTest() {
        Mockito.doNothing().when(movieManagerServiceMock).removeMovieById(Mockito.anyInt());
        movieController.deleteMovie(1);
    }
    //endregion addMovie

}
