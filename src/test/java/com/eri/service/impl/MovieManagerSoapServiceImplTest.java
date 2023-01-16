package com.eri.service.impl;

import com.eri.constant.enums.CacheKey;
import com.eri.converter.mapstruct.IMovieMapper;
import com.eri.exception.CacheNotInitializedException;
import com.eri.exception.MovieNotFoundException;
import com.eri.generated.movieapi.stub.ListMoviesRequest;
import com.eri.generated.movieapi.stub.ListMoviesResponse;
import com.eri.helper.MovieDataHelper;
import com.eri.helper.MovieSoapDataHelper;
import com.eri.model.Movie;
import com.eri.model.messaging.EventMessage;
import com.eri.service.cache.ICacheService;
import com.eri.service.messaging.IMessageService;
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
    private MovieManagerSoapServiceImpl movieManagerSoapService;

    /*
    // I do not know why I have to use a captor to mock RestTemplate.exchange
    // but without it, Mockito is not mocking RestTemplate.exchange.
    */
    @Captor
    private ArgumentCaptor<Object> argumentCaptor;

    //region mocks
    @Mock
    private ICacheService cacheServiceMock;

    @Mock
    private IMessageService messageServiceMock;

    @Mock
    private IMovieMapper movieMapperMock;

    @Mock
    private SoapClient soapClientMock;
    //endregion mocks

    //region fields
    private MovieSoapDataHelper soapDataHelper;

    private MovieDataHelper dataHelper;

    private List<Movie> movieList;
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
        Mockito.when(soapClientMock.listMovies((String) argumentCaptor.capture(), Mockito.any(ListMoviesRequest.class))).thenReturn(listMoviesResponse);
        Mockito.when(movieMapperMock.generatedToModel(listMoviesResponse.getMovies().get(0))).thenReturn(movieList.get(0));
        Mockito.when(movieMapperMock.generatedToModel(listMoviesResponse.getMovies().get(1))).thenReturn(movieList.get(1));
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
        Mockito.doReturn(movieList).when(cacheServiceMock).findListFromCacheWithKey(Mockito.anyString());
        // actual method call
        List<Movie> moviesResponse = movieManagerSoapService.getMovies(fromCache);
        // assertions
        Assert.assertEquals(movieList, moviesResponse);
    }

    @Test(expected = CacheNotInitializedException.class)
    public void getMoviesWithNullCacheTest(){
        boolean fromCache = true;
        // mocking
        Mockito.doReturn(null).when(cacheServiceMock).findListFromCacheWithKey(Mockito.anyString());
        // actual method call
        movieManagerSoapService.getMovies(fromCache);
    }
    //endregion getMovies

    //region listNewcomers
    @Test
    public void listNewComersTest(){
        // mocking
        Mockito.doReturn(movieList).when(cacheServiceMock).findListFromCacheWithKey(Mockito.eq(CacheKey.NEWCOMERS.getName()));
        // actual method call
        List<Movie> moviesActual = movieManagerSoapService.listNewComers();
        // assertions
        Assert.assertEquals(movieList, moviesActual);
    }

    @Test
    public void listNewComersNullCacheTest(){
        // mocking
        Mockito.when(cacheServiceMock.findListFromCacheWithKey(Mockito.eq(CacheKey.NEWCOMERS.getName()))).thenReturn(null);
        // actual method call
        List<Movie> moviesActual = movieManagerSoapService.listNewComers();
        // assertions
        Assert.assertTrue(moviesActual.isEmpty());
    }
    //endregion listNewComers

    //region findMovieById
    @Test
    public void findMovieByIdTest(){
        int id = 1;
        ListMoviesResponse listMoviesResponse = soapDataHelper.getMovieResponse();
        // mocking
        Mockito.when(soapClientMock.listMovies((String) argumentCaptor.capture(), Mockito.any(ListMoviesRequest.class))).thenReturn(listMoviesResponse);
        Mockito.when(movieMapperMock.generatedToModel(listMoviesResponse.getMovies().get(0))).thenReturn(movieList.get(0));
        // actual method call
        Movie movie = movieManagerSoapService.findMovieById(id);
        // assertions
        Assert.assertEquals(id, movie.getId());
    }

    @Test(expected = MovieNotFoundException.class)
    public void findMovieByIdMovieNotFoundTest(){
        // mocking
        Mockito.when(soapClientMock.listMovies((String) argumentCaptor.capture(), Mockito.any(ListMoviesRequest.class))).thenReturn(new ListMoviesResponse());
        // actual method call
        movieManagerSoapService.findMovieById(1);
    }
    //endregion findMovieById

    //region addMovie
    @Test
    public void addMovieTest(){
        Movie movie = dataHelper.createRandomMovie(3);
        // mocking
        Mockito.doNothing().when(messageServiceMock).sendMessage(Mockito.any(EventMessage.class));
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
