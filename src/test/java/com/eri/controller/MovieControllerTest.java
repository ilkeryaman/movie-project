package com.eri.controller;

import com.eri.helper.MovieDataHelper;
import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
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
public class MovieControllerTest {
    @InjectMocks
    MovieController movieController;

    //region mocks
    @Mock
    IMovieManagerService movieManagerServiceMock;
    //endregion mocks

    //region fields
    MovieDataHelper dataHelper;

    List<Movie> movies;
    //endregion fields

    @Before
    public void init(){
        dataHelper = new MovieDataHelper();
        movies = dataHelper.getMovieList();
    }

    //region listMovies
    @Test
    public void listMoviesWithoutIdTest(){
        boolean fromCache = false;
        // mocking
        Mockito.when(movieManagerServiceMock.getMovies(fromCache)).thenReturn(movies);
        // actual method call
        List<Movie> moviesActual = movieController.listMovies(null, fromCache);
        // assertions
        Assert.assertEquals(movies, moviesActual);
    }

    @Test
    public void listMoviesWithoutIdFromCacheTest(){
        boolean fromCache = true;
        // mocking
        Mockito.when(movieManagerServiceMock.getMovies(fromCache)).thenReturn(movies);
        // actual method call
        List<Movie> moviesActual = movieController.listMovies(null, fromCache);
        // assertions
        Assert.assertEquals(movies, moviesActual);
    }

    @Test
    public void listMoviesWithIdTest(){
        // mocking
        Mockito.when(movieManagerServiceMock.findMovieById(Mockito.anyInt())).thenReturn(movies.get(0));
        // actual method call
        List<Movie> moviesActual = movieController.listMovies(1, false);
        // assertions
        Assert.assertEquals(movies.get(0).getId(), moviesActual.get(0).getId());
        Assert.assertEquals(movies.get(0).getTitle(), moviesActual.get(0).getTitle());
        Assert.assertTrue(movies.get(0).getCategories().size() == moviesActual.get(0).getCategories().size());
        Assert.assertEquals(movies.get(0).getCategories().get(0), moviesActual.get(0).getCategories().get(0));
        Assert.assertEquals(movies.get(0).getCategories().get(1), moviesActual.get(0).getCategories().get(1));
        Assert.assertTrue(movies.get(0).getDirectors().size() == moviesActual.get(0).getDirectors().size());
        Assert.assertEquals(movies.get(0).getDirectors().get(0).getName(), moviesActual.get(0).getDirectors().get(0).getName());
        Assert.assertEquals(movies.get(0).getDirectors().get(0).getSurname(), moviesActual.get(0).getDirectors().get(0).getSurname());
        Assert.assertTrue(movies.get(0).getStars().size() == moviesActual.get(0).getStars().size());
        Assert.assertEquals(movies.get(0).getStars().get(0).getName(), moviesActual.get(0).getStars().get(0).getName());
        Assert.assertEquals(movies.get(0).getStars().get(0).getSurname(), moviesActual.get(0).getStars().get(0).getSurname());
    }

    @Test
    public void listMoviesWithIdNullTest(){
        // mocking
        Mockito.when(movieManagerServiceMock.findMovieById(Mockito.anyInt())).thenReturn(null);
        // actual method call
        List<Movie> moviesActual = movieController.listMovies(1, false);
        // assertions
        Assert.assertEquals(Collections.emptyList(), moviesActual);
    }
    //endregion listMovies


    //region getMovies
    @Test
    public void getMoviesTest(){
        Movie movie = dataHelper.getMovie();
        // mocking
        Mockito.when(movieManagerServiceMock.findMovieById(Mockito.anyInt())).thenReturn(movie);
        // actual method call
        Movie movieActual = movieController.getMovie(1);
        // assertions
        Assert.assertEquals(movie, movieActual);
    }
    //endregion getMovies

    //region addMovie
    @Test
    public void addMovieTest() {
        // mocking
        Mockito.doNothing().when(movieManagerServiceMock).addMovie(Mockito.any(Movie.class));
        // actual method call
        movieController.addMovie(dataHelper.getMovie());
    }
    //endregion addMovie

    //region addMovie
    @Test
    public void deleteMovieTest() {
        // mocking
        Mockito.doNothing().when(movieManagerServiceMock).removeMovieById(Mockito.anyInt());
        // actual method call
        movieController.deleteMovie(1);
    }
    //endregion addMovie

}
