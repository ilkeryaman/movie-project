package com.eri.service.impl;

import com.eri.constant.enums.CacheKey;
import com.eri.converter.mapstruct.IMovieRestMapper;
import com.eri.exception.CacheNotInitializedException;
import com.eri.helper.MovieDataHelper;
import com.eri.helper.MovieRestDataHelper;
import com.eri.model.Movie;
import com.eri.model.messaging.EventMessage;
import com.eri.service.cache.ICacheService;
import com.eri.service.messaging.IMessageService;
import org.apache.http.HttpHeaders;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MovieManagerRestServiceWebClientImplTest {
    @InjectMocks
    private MovieManagerRestServiceWebClientImpl movieManagerRestServiceWebClient;

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
    private IMovieRestMapper restMapperMock;

    @Mock
    private WebClient webClientMock;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    private WebClient.RequestBodySpec requestBodySpecMock;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;

    @Mock
    private WebClient.ResponseSpec responseSpecMock;
    //endregion mocks

    //region fields
    private MovieDataHelper dataHelper;

    private MovieRestDataHelper restDataHelper;

    private List<com.eri.swagger.movie_api.model.Movie> movieRestList;

    private List<Movie> movieList;
    //endregion fields

    @Before
    public void init(){
        dataHelper = new MovieDataHelper();
        restDataHelper = new MovieRestDataHelper();
        movieList = dataHelper.getMovieList();
        movieRestList = restDataHelper.getMovieList();
    }

    //region getMovies
    @Test
    public void getMoviesTest(){
        boolean fromCache = false;
        // mocking
        Mockito.when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        Mockito.when(requestHeadersUriSpecMock.uri((String) argumentCaptor.capture())).thenReturn(requestHeadersSpecMock);
        Mockito.when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToMono(com.eri.swagger.movie_api.model.Movie[].class)).thenReturn(Mono.just(movieRestList.toArray(new com.eri.swagger.movie_api.model.Movie[0])));
        Mockito.when(restMapperMock.generatedToModel(movieRestList.get(0))).thenReturn(movieList.get(0));
        Mockito.when(restMapperMock.generatedToModel(movieRestList.get(1))).thenReturn(movieList.get(1));
        // actual method call
        List<Movie> moviesActual = movieManagerRestServiceWebClient.getMovies(fromCache);
        // assertions
        Assert.assertEquals(movieList.size(), moviesActual.size());
        Assert.assertEquals(movieList.get(0).getId(), moviesActual.get(0).getId());
        Assert.assertEquals(movieList.get(0).getTitle(), moviesActual.get(0).getTitle());
        Assert.assertEquals(movieList.get(0).getCategories().size(), moviesActual.get(0).getCategories().size());
        Assert.assertEquals(movieList.get(0).getCategories().get(0), moviesActual.get(0).getCategories().get(0));
        Assert.assertEquals(movieList.get(0).getCategories().get(1), moviesActual.get(0).getCategories().get(1));
        Assert.assertEquals(movieList.get(0).getDirectors().size(), moviesActual.get(0).getDirectors().size());
        Assert.assertEquals(movieList.get(0).getDirectors().get(0).getName(), moviesActual.get(0).getDirectors().get(0).getName());
        Assert.assertEquals(movieList.get(0).getDirectors().get(0).getSurname(), moviesActual.get(0).getDirectors().get(0).getSurname());
        Assert.assertEquals(movieList.get(0).getStars().size(), moviesActual.get(0).getStars().size());
        Assert.assertEquals(movieList.get(0).getStars().get(0).getName(), moviesActual.get(0).getStars().get(0).getName());
        Assert.assertEquals(movieList.get(0).getStars().get(0).getSurname(), moviesActual.get(0).getStars().get(0).getSurname());
        Assert.assertEquals(movieList.get(1).getId(), moviesActual.get(1).getId());
        Assert.assertEquals(movieList.get(1).getTitle(), moviesActual.get(1).getTitle());
        Assert.assertEquals(movieList.get(1).getCategories().size(), moviesActual.get(1).getCategories().size());
        Assert.assertEquals(movieList.get(1).getCategories().get(0), moviesActual.get(1).getCategories().get(0));
        Assert.assertEquals(movieList.get(1).getCategories().get(1), moviesActual.get(1).getCategories().get(1));
        Assert.assertEquals(movieList.get(1).getCategories().get(2), moviesActual.get(1).getCategories().get(2));
        Assert.assertEquals(movieList.get(1).getDirectors().size(), moviesActual.get(1).getDirectors().size());
        Assert.assertEquals(movieList.get(1).getDirectors().get(0).getName(), moviesActual.get(1).getDirectors().get(0).getName());
        Assert.assertEquals(movieList.get(1).getDirectors().get(0).getSurname(), moviesActual.get(1).getDirectors().get(0).getSurname());
        Assert.assertEquals(movieList.get(1).getStars().size(), moviesActual.get(1).getStars().size());
        Assert.assertEquals(movieList.get(1).getStars().get(0).getName(), moviesActual.get(1).getStars().get(0).getName());
        Assert.assertEquals(movieList.get(1).getStars().get(0).getSurname(), moviesActual.get(1).getStars().get(0).getSurname());
    }

    @Test
    public void getMoviesFromCacheTest(){
        boolean fromCache = true;
        // mocking
        Mockito.doReturn(movieList).when(cacheServiceMock).findListFromCacheWithKey(CacheKey.MOVIES.getName());
        // actual method call
        List<Movie> moviesActual = movieManagerRestServiceWebClient.getMovies(fromCache);
        // assertions
        Assert.assertEquals(movieList, moviesActual);
    }

    @Test(expected = CacheNotInitializedException.class)
    public void getMoviesWithNullCacheTest(){
        boolean fromCache = true;
        // mocking
        Mockito.when(cacheServiceMock.findListFromCacheWithKey(CacheKey.MOVIES.getName())).thenReturn(null);
        // actual method call
        movieManagerRestServiceWebClient.getMovies(fromCache);
    }
    //endregion getMovies

    //region listNewcomers
    @Test
    public void listNewComersTest(){
        // mocking
        Mockito.doReturn(movieList).when(cacheServiceMock).findListFromCacheWithKey(Mockito.eq(CacheKey.NEWCOMERS.getName()));
        // actual method call
        List<Movie> moviesActual = movieManagerRestServiceWebClient.listNewComers();
        // assertions
        Assert.assertEquals(movieList, moviesActual);
    }

    @Test
    public void listNewComersNullCacheTest(){
        // mocking
        Mockito.when(cacheServiceMock.findListFromCacheWithKey(Mockito.eq(CacheKey.NEWCOMERS.getName()))).thenReturn(null);
        // actual method call
        List<Movie> moviesActual = movieManagerRestServiceWebClient.listNewComers();
        // assertions
        Assert.assertTrue(moviesActual.isEmpty());
    }
    //endregion listNewComers

    //region findMovieById
    @Test
    public void findMovieByIdTest(){
        // mocking
        Mockito.when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        Mockito.when(requestHeadersUriSpecMock.uri((String) argumentCaptor.capture())).thenReturn(requestHeadersSpecMock);
        Mockito.when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToMono(com.eri.swagger.movie_api.model.Movie.class)).thenReturn(Mono.just(movieRestList.get(0)));
        Mockito.when(restMapperMock.generatedToModel(movieRestList.get(0))).thenReturn(movieList.get(0));
        // actual method call
        Movie movieActual = movieManagerRestServiceWebClient.findMovieById(1);
        // assertions
        Assert.assertEquals(movieList.get(0).getId(), movieActual.getId());
        Assert.assertEquals(movieList.get(0).getTitle(), movieActual.getTitle());
        Assert.assertEquals(movieList.get(0).getCategories().size(), movieActual.getCategories().size());
        Assert.assertEquals(movieList.get(0).getCategories().get(0), movieActual.getCategories().get(0));
        Assert.assertEquals(movieList.get(0).getCategories().get(1), movieActual.getCategories().get(1));
        Assert.assertEquals(movieList.get(0).getDirectors().size(), movieActual.getDirectors().size());
        Assert.assertEquals(movieList.get(0).getDirectors().get(0).getName(), movieActual.getDirectors().get(0).getName());
        Assert.assertEquals(movieList.get(0).getDirectors().get(0).getSurname(), movieActual.getDirectors().get(0).getSurname());
        Assert.assertEquals(movieList.get(0).getStars().size(), movieActual.getStars().size());
        Assert.assertEquals(movieList.get(0).getStars().get(0).getName(), movieActual.getStars().get(0).getName());
        Assert.assertEquals(movieList.get(0).getStars().get(0).getSurname(), movieActual.getStars().get(0).getSurname());
    }

    @Test
    public void findMovieByIdMovieNotFoundTest(){
        // mocking
        Mockito.when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        Mockito.when(requestHeadersUriSpecMock.uri(Mockito.anyString())).thenReturn(requestHeadersSpecMock);
        Mockito.when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToMono(com.eri.swagger.movie_api.model.Movie.class)).thenReturn(Mono.empty());
        Mockito.when(restMapperMock.generatedToModel(null)).thenReturn(null);
        // actual method call
        Movie movieActual = movieManagerRestServiceWebClient.findMovieById(1200);
        // assertions
        Assert.assertNull(movieActual);
    }
    //endregion findMovieById

    //region addMovie
    @Test
    public void addMovieTest(){
        int id = 3;
        Mono<ResponseEntity<Void>> responseEntity = Mono.just(new ResponseEntity<Void>(HttpStatus.CREATED));
        Movie movie = dataHelper.createRandomMovie(id);
        com.eri.swagger.movie_api.model.Movie movieRest = restDataHelper.createRandomMovie(id);
        // mocking
        Mockito.when(restMapperMock.modelToGenerated(movie)).thenReturn(movieRest);
        Mockito.when(webClientMock.post()).thenReturn(requestBodyUriSpecMock);
        Mockito.when(requestBodyUriSpecMock.uri((String) argumentCaptor.capture())).thenReturn(requestBodySpecMock);
        Mockito.when(requestBodySpecMock.header(Mockito.eq(HttpHeaders.CONTENT_TYPE), Mockito.eq(MediaType.APPLICATION_JSON_VALUE))).thenReturn(requestBodySpecMock);
        Mockito.when(requestBodySpecMock.body(Mockito.any(Mono.class), Mockito.eq(com.eri.swagger.movie_api.model.Movie.class))).thenReturn(requestHeadersSpecMock);
        Mockito.when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.toBodilessEntity()).thenReturn(responseEntity);
        Mockito.doNothing().when(messageServiceMock).sendMessage(Mockito.any(EventMessage.class));
        // actual method call
        movieManagerRestServiceWebClient.addMovie(movie);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void addMovieBadRequestExceptionTest(){
        int id = 3;
        Movie movie = dataHelper.createRandomMovie(id);
        com.eri.swagger.movie_api.model.Movie movieRest = restDataHelper.createRandomMovie(id);
        // mocking
        Mockito.when(restMapperMock.modelToGenerated(movie)).thenReturn(movieRest);
        Mockito.when(webClientMock.post()).thenReturn(requestBodyUriSpecMock);
        Mockito.when(requestBodyUriSpecMock.uri((String) argumentCaptor.capture())).thenReturn(requestBodySpecMock);
        Mockito.when(requestBodySpecMock.header(Mockito.eq(HttpHeaders.CONTENT_TYPE), Mockito.eq(MediaType.APPLICATION_JSON_VALUE))).thenReturn(requestBodySpecMock);
        Mockito.when(requestBodySpecMock.body(Mockito.any(Mono.class), Mockito.eq(com.eri.swagger.movie_api.model.Movie.class))).thenReturn(requestHeadersSpecMock);
        Mockito.doThrow(HttpClientErrorException.BadRequest.class).when(requestHeadersSpecMock).retrieve();
        // actual method call
        movieManagerRestServiceWebClient.addMovie(movie);
    }
    //endregion addMovie

    //region removeMovieById
    @Test
    public void removeMovieByIdTest(){
        // mocking
        Mockito.when(webClientMock.delete()).thenReturn(requestHeadersUriSpecMock);
        Mockito.when(requestHeadersUriSpecMock.uri((String) argumentCaptor.capture())).thenReturn(requestHeadersSpecMock);
        Mockito.when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToMono(Mockito.eq(Void.class))).thenReturn(Mono.empty());
        // actual method call
        movieManagerRestServiceWebClient.removeMovieById(1);
    }
    //endregion removeMovieById
}
