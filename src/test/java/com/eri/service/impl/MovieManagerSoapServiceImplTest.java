package com.eri.service.impl;

import com.eri.base.MovieProjectJUnitTestBase;
import com.eri.exception.CacheNotInitializedException;
import com.eri.exception.MovieNotFoundException;
import com.eri.generated.movieapi.stub.AddMovieRequest;
import com.eri.generated.movieapi.stub.DeleteMovieRequest;
import com.eri.generated.movieapi.stub.ListMoviesRequest;
import com.eri.generated.movieapi.stub.ListMoviesResponse;
import com.eri.helper.MovieDataHelper;
import com.eri.helper.MovieSoapDataHelper;
import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
import com.eri.service.cache.ICacheService;
import com.eri.service.soap.SoapClient;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.util.List;

public class MovieManagerSoapServiceImplTest extends MovieProjectJUnitTestBase {

    @Resource
    MovieSoapDataHelper soapDataHelper;

    @Resource
    MovieDataHelper dataHelper;

    @Resource(name = "movieManagerSoapService")
    IMovieManagerService movieManagerService;

    @MockBean
    ICacheService cacheService;

    @MockBean
    SoapClient soapClient;

    //region getMovies
    @Test
    public void getMoviesTest(){
        boolean fromCache = false;
        ListMoviesResponse moviesResponseExpected = soapDataHelper.getExpectedMovieList();
        Mockito.when(soapClient.listMovies(Mockito.anyString(), Mockito.any(ListMoviesRequest.class))).thenReturn(moviesResponseExpected);
        List<Movie> movieResponse = movieManagerService.getMovies(fromCache);
        Assert.assertEquals(moviesResponseExpected.getMovies().size(), movieResponse.size());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(0).getId().intValue(), movieResponse.get(0).getId());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(0).getTitle(), movieResponse.get(0).getTitle());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(0).getCategories().size(), movieResponse.get(0).getCategories().size());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(0).getCategories().get(0), movieResponse.get(0).getCategories().get(0));
        Assert.assertEquals(moviesResponseExpected.getMovies().get(0).getCategories().get(1), movieResponse.get(0).getCategories().get(1));
        Assert.assertEquals(moviesResponseExpected.getMovies().get(0).getDirectors().size(), movieResponse.get(0).getDirectors().size());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(0).getDirectors().get(0).getName(), movieResponse.get(0).getDirectors().get(0).getName());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(0).getDirectors().get(0).getSurname(), movieResponse.get(0).getDirectors().get(0).getSurname());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(0).getStars().size(), movieResponse.get(0).getStars().size());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(0).getStars().get(0).getName(), movieResponse.get(0).getStars().get(0).getName());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(0).getStars().get(0).getSurname(), movieResponse.get(0).getStars().get(0).getSurname());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(1).getId().intValue(), movieResponse.get(1).getId());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(1).getTitle(), movieResponse.get(1).getTitle());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(1).getCategories().size(), movieResponse.get(1).getCategories().size());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(1).getCategories().get(0), movieResponse.get(1).getCategories().get(0));
        Assert.assertEquals(moviesResponseExpected.getMovies().get(1).getCategories().get(1), movieResponse.get(1).getCategories().get(1));
        Assert.assertEquals(moviesResponseExpected.getMovies().get(1).getDirectors().size(), movieResponse.get(1).getDirectors().size());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(1).getDirectors().get(0).getName(), movieResponse.get(1).getDirectors().get(0).getName());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(1).getDirectors().get(0).getSurname(), movieResponse.get(1).getDirectors().get(0).getSurname());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(1).getStars().size(), movieResponse.get(1).getStars().size());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(1).getStars().get(0).getName(), movieResponse.get(1).getStars().get(0).getName());
        Assert.assertEquals(moviesResponseExpected.getMovies().get(1).getStars().get(0).getSurname(), movieResponse.get(1).getStars().get(0).getSurname());
    }

    @Test
    public void getMoviesFromCacheTest(){
        boolean fromCache = true;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        Mockito.doReturn(moviesExpected).when(cacheService).findListFromCacheWithKey(Mockito.anyString());
        List<Movie> moviesResponse = movieManagerService.getMovies(fromCache);
        Assert.assertEquals(moviesExpected, moviesResponse);
    }

    @Test(expected = CacheNotInitializedException.class)
    public void getMoviesWithNullCacheTest(){
        boolean fromCache = true;
        Mockito.doReturn(null).when(cacheService).findListFromCacheWithKey(Mockito.anyString());
        movieManagerService.getMovies(fromCache);
    }
    //endregion getMovies

    //region findMovieById
    @Test
    public void findMovieByIdTest(){
        int id = 1;
        ListMoviesResponse moviesResponseExpected = soapDataHelper.getExpectedMovieList();
        Mockito.when(soapClient.listMovies(Mockito.anyString(), Mockito.any(ListMoviesRequest.class))).thenReturn(moviesResponseExpected);
        Movie movie = movieManagerService.findMovieById(id);
        Assert.assertEquals(id, movie.getId());
    }

    @Test(expected = MovieNotFoundException.class)
    public void findMovieByIdMovieNotFoundTest(){
        Mockito.when(soapClient.listMovies(Mockito.anyString(), Mockito.any(ListMoviesRequest.class))).thenReturn(new ListMoviesResponse());
        movieManagerService.findMovieById(1);
    }
    //endregion findMovieById

    //region addMovie
    @Test
    public void addMovieTest(){
        Movie movie = dataHelper.getExpectedMovie();
        Mockito.doNothing().when(soapClient).addMovie(Mockito.anyString(), Mockito.any(AddMovieRequest.class));
        movieManagerService.addMovie(movie);
    }
    //endregion addMovie

    //region removeMovieById
    @Test
    public void removeMovieByIdTest(){
        Movie movie = dataHelper.getExpectedMovie();
        Mockito.doNothing().when(soapClient).deleteMovie(Mockito.anyString(), Mockito.any(DeleteMovieRequest.class));
        movieManagerService.removeMovieById(1);
    }
    //endregion removeMovieById
}
