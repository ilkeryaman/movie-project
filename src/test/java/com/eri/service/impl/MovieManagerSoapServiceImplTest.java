package com.eri.service.impl;

import com.eri.converter.mapstruct.IMovieMapper;
import com.eri.exception.CacheNotInitializedException;
import com.eri.exception.MovieNotFoundException;
import com.eri.generated.movieapi.stub.ListMoviesRequest;
import com.eri.generated.movieapi.stub.ListMoviesResponse;
import com.eri.helper.MovieDataHelper;
import com.eri.helper.MovieSoapDataHelper;
import com.eri.model.Movie;
import com.eri.service.cache.ICacheService;
import com.eri.service.soap.SoapClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MovieManagerSoapServiceImplTest {
    @InjectMocks
    MovieManagerSoapServiceImpl movieManagerSoapService;

    /*
    // I do not know why I have to use a captor to mock RestTemplate.exchange
    // but without it, Mockito is not mocking RestTemplate.exchange.
    */
    @Captor
    ArgumentCaptor<Object> argumentCaptor;

    //region mocks
    @Mock
    ICacheService cacheService;

    @Mock
    IMovieMapper movieMapper;

    @Mock
    SoapClient soapClient;
    //endregion mocks

    //region fields
    MovieSoapDataHelper soapDataHelper;

    MovieDataHelper dataHelper;

    List<Movie> movieList;
    //endregion fields

    @Before
    public void init(){
        dataHelper = new MovieDataHelper();
        soapDataHelper = new MovieSoapDataHelper();
        movieList = dataHelper.getMovieList();
    }

    //region getMovies
    @Test
    public void getMoviesTest(){
        boolean fromCache = false;
        ListMoviesResponse listMoviesResponse = soapDataHelper.getMovieListResponse();
        // mocking
        Mockito.when(soapClient.listMovies((String) argumentCaptor.capture(), Mockito.any(ListMoviesRequest.class))).thenReturn(listMoviesResponse);
        Mockito.when(movieMapper.generatedToModel(listMoviesResponse.getMovies().get(0))).thenReturn(movieList.get(0));
        Mockito.when(movieMapper.generatedToModel(listMoviesResponse.getMovies().get(1))).thenReturn(movieList.get(1));
        // actual method call
        List<Movie> moviesActual = movieManagerSoapService.getMovies(fromCache);
        // assertions
        Assert.assertEquals(listMoviesResponse.getMovies().size(), moviesActual.size());
        Assert.assertEquals(listMoviesResponse.getMovies().get(0).getId().intValue(), moviesActual.get(0).getId());
        Assert.assertEquals(listMoviesResponse.getMovies().get(0).getTitle(), moviesActual.get(0).getTitle());
        Assert.assertEquals(listMoviesResponse.getMovies().get(0).getCategories().size(), moviesActual.get(0).getCategories().size());
        Assert.assertEquals(listMoviesResponse.getMovies().get(0).getCategories().get(0), moviesActual.get(0).getCategories().get(0));
        Assert.assertEquals(listMoviesResponse.getMovies().get(0).getCategories().get(1), moviesActual.get(0).getCategories().get(1));
        Assert.assertEquals(listMoviesResponse.getMovies().get(0).getDirectors().size(), moviesActual.get(0).getDirectors().size());
        Assert.assertEquals(listMoviesResponse.getMovies().get(0).getDirectors().get(0).getName(), moviesActual.get(0).getDirectors().get(0).getName());
        Assert.assertEquals(listMoviesResponse.getMovies().get(0).getDirectors().get(0).getSurname(), moviesActual.get(0).getDirectors().get(0).getSurname());
        Assert.assertEquals(listMoviesResponse.getMovies().get(0).getStars().size(), moviesActual.get(0).getStars().size());
        Assert.assertEquals(listMoviesResponse.getMovies().get(0).getStars().get(0).getName(), moviesActual.get(0).getStars().get(0).getName());
        Assert.assertEquals(listMoviesResponse.getMovies().get(0).getStars().get(0).getSurname(), moviesActual.get(0).getStars().get(0).getSurname());
        Assert.assertEquals(listMoviesResponse.getMovies().get(1).getId().intValue(), moviesActual.get(1).getId());
        Assert.assertEquals(listMoviesResponse.getMovies().get(1).getTitle(), moviesActual.get(1).getTitle());
        Assert.assertEquals(listMoviesResponse.getMovies().get(1).getCategories().size(), moviesActual.get(1).getCategories().size());
        Assert.assertEquals(listMoviesResponse.getMovies().get(1).getCategories().get(0), moviesActual.get(1).getCategories().get(0));
        Assert.assertEquals(listMoviesResponse.getMovies().get(1).getCategories().get(1), moviesActual.get(1).getCategories().get(1));
        Assert.assertEquals(listMoviesResponse.getMovies().get(1).getDirectors().size(), moviesActual.get(1).getDirectors().size());
        Assert.assertEquals(listMoviesResponse.getMovies().get(1).getDirectors().get(0).getName(), moviesActual.get(1).getDirectors().get(0).getName());
        Assert.assertEquals(listMoviesResponse.getMovies().get(1).getDirectors().get(0).getSurname(), moviesActual.get(1).getDirectors().get(0).getSurname());
        Assert.assertEquals(listMoviesResponse.getMovies().get(1).getStars().size(), moviesActual.get(1).getStars().size());
        Assert.assertEquals(listMoviesResponse.getMovies().get(1).getStars().get(0).getName(), moviesActual.get(1).getStars().get(0).getName());
        Assert.assertEquals(listMoviesResponse.getMovies().get(1).getStars().get(0).getSurname(), moviesActual.get(1).getStars().get(0).getSurname());
    }

    @Test
    public void getMoviesFromCacheTest(){
        boolean fromCache = true;
        // mocking
        Mockito.doReturn(movieList).when(cacheService).findListFromCacheWithKey(Mockito.anyString());
        // actual method call
        List<Movie> moviesResponse = movieManagerSoapService.getMovies(fromCache);
        // assertions
        Assert.assertEquals(movieList, moviesResponse);
    }

    @Test(expected = CacheNotInitializedException.class)
    public void getMoviesWithNullCacheTest(){
        boolean fromCache = true;
        // mocking
        Mockito.doReturn(null).when(cacheService).findListFromCacheWithKey(Mockito.anyString());
        // actual method call
        movieManagerSoapService.getMovies(fromCache);
    }
    //endregion getMovies

    //region findMovieById
    @Test
    public void findMovieByIdTest(){
        int id = 1;
        ListMoviesResponse listMoviesResponse = soapDataHelper.getMovieResponse();
        // mocking
        Mockito.when(soapClient.listMovies((String) argumentCaptor.capture(), Mockito.any(ListMoviesRequest.class))).thenReturn(listMoviesResponse);
        Mockito.when(movieMapper.generatedToModel(listMoviesResponse.getMovies().get(0))).thenReturn(movieList.get(0));
        // actual method call
        Movie movie = movieManagerSoapService.findMovieById(id);
        // assertions
        Assert.assertEquals(id, movie.getId());
    }

    @Test(expected = MovieNotFoundException.class)
    public void findMovieByIdMovieNotFoundTest(){
        // mocking
        Mockito.when(soapClient.listMovies((String) argumentCaptor.capture(), Mockito.any(ListMoviesRequest.class))).thenReturn(new ListMoviesResponse());
        // actual method call
        movieManagerSoapService.findMovieById(1);
    }
    //endregion findMovieById

    //region addMovie
    @Test
    public void addMovieTest(){
        Movie movie = dataHelper.createRandomMovie(3);
        // actual method call
        movieManagerSoapService.addMovie(movie);
    }
    //endregion addMovie

    //region removeMovieById
    @Test
    public void removeMovieByIdTest(){
        // actual method call
        movieManagerSoapService.removeMovieById(1);
    }
    //endregion removeMovieById
}
