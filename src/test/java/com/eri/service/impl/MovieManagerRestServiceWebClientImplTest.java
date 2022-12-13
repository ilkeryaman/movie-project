package com.eri.service.impl;

import com.eri.base.MovieProjectJUnitTestBase;
import com.eri.constant.enums.CacheKey;
import com.eri.exception.CacheNotInitializedException;
import com.eri.helper.MovieDataHelper;
import com.eri.helper.MovieRestDataHelper;
import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
import com.eri.service.cache.ICacheService;
import org.apache.http.HttpHeaders;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

public class MovieManagerRestServiceWebClientImplTest extends MovieProjectJUnitTestBase {
    @Resource
    MovieDataHelper dataHelper;

    @Resource
    MovieRestDataHelper restDataHelper;

    @Resource(name = "movieManagerWebClientService")
    IMovieManagerService movieManagerService;

    @MockBean
    ICacheService cacheServiceMock;

    @MockBean
    WebClient webClientMock;

    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    WebClient.RequestBodySpec requestBodySpecMock;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @Mock
    WebClient.RequestBodyUriSpec requestBodyUriSpecMock;

    @Mock
    WebClient.ResponseSpec responseSpecMock;

    //region getMovies
    @Test
    public void getMoviesTest(){
        boolean fromCache = false;
        List<com.eri.swagger.movie_api.model.Movie> moviesExpected = restDataHelper.getExpectedMovieList(fromCache);
        Mockito.when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        Mockito.when(requestHeadersUriSpecMock.uri(Mockito.anyString())).thenReturn(requestHeadersSpecMock);
        Mockito.when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToMono(com.eri.swagger.movie_api.model.Movie[].class)).thenReturn(Mono.just(moviesExpected.toArray(new com.eri.swagger.movie_api.model.Movie[0])));
        List<Movie> moviesActual = movieManagerService.getMovies(fromCache);
        Assert.assertEquals(moviesExpected.size(), moviesActual.size());
        Assert.assertEquals(moviesExpected.get(0).getId().intValue(), moviesActual.get(0).getId());
        Assert.assertEquals(moviesExpected.get(0).getTitle(), moviesActual.get(0).getTitle());
        Assert.assertEquals(moviesExpected.get(0).getCategories().size(), moviesActual.get(0).getCategories().size());
        Assert.assertEquals(moviesExpected.get(0).getCategories().get(0), moviesActual.get(0).getCategories().get(0));
        Assert.assertEquals(moviesExpected.get(0).getCategories().get(1), moviesActual.get(0).getCategories().get(1));
        Assert.assertEquals(moviesExpected.get(0).getDirectors().size(), moviesActual.get(0).getDirectors().size());
        Assert.assertEquals(moviesExpected.get(0).getDirectors().get(0).getName(), moviesActual.get(0).getDirectors().get(0).getName());
        Assert.assertEquals(moviesExpected.get(0).getDirectors().get(0).getSurname(), moviesActual.get(0).getDirectors().get(0).getSurname());
        Assert.assertEquals(moviesExpected.get(0).getStars().size(), moviesActual.get(0).getStars().size());
        Assert.assertEquals(moviesExpected.get(0).getStars().get(0).getName(), moviesActual.get(0).getStars().get(0).getName());
        Assert.assertEquals(moviesExpected.get(0).getStars().get(0).getSurname(), moviesActual.get(0).getStars().get(0).getSurname());
        Assert.assertEquals(moviesExpected.get(1).getId().intValue(), moviesActual.get(1).getId());
        Assert.assertEquals(moviesExpected.get(1).getTitle(), moviesActual.get(1).getTitle());
        Assert.assertEquals(moviesExpected.get(1).getCategories().size(), moviesActual.get(1).getCategories().size());
        Assert.assertEquals(moviesExpected.get(1).getCategories().get(0), moviesActual.get(1).getCategories().get(0));
        Assert.assertEquals(moviesExpected.get(1).getCategories().get(1), moviesActual.get(1).getCategories().get(1));
        Assert.assertEquals(moviesExpected.get(1).getDirectors().size(), moviesActual.get(1).getDirectors().size());
        Assert.assertEquals(moviesExpected.get(1).getDirectors().get(0).getName(), moviesActual.get(1).getDirectors().get(0).getName());
        Assert.assertEquals(moviesExpected.get(1).getDirectors().get(0).getSurname(), moviesActual.get(1).getDirectors().get(0).getSurname());
        Assert.assertEquals(moviesExpected.get(1).getStars().size(), moviesActual.get(1).getStars().size());
        Assert.assertEquals(moviesExpected.get(1).getStars().get(0).getName(), moviesActual.get(1).getStars().get(0).getName());
        Assert.assertEquals(moviesExpected.get(1).getStars().get(0).getSurname(), moviesActual.get(1).getStars().get(0).getSurname());
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
    public void getMoviesWithNullCacheTest(){
        boolean fromCache = true;
        Mockito.when(cacheServiceMock.findListFromCacheWithKey(CacheKey.MOVIES.getName())).thenReturn(null);
        movieManagerService.getMovies(fromCache);
    }
    //endregion getMovies

    //region findMovieById
    @Test
    public void findMovieByIdTest(){
        com.eri.swagger.movie_api.model.Movie movieExpected = restDataHelper.getExpectedMovie();
        Mockito.when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        Mockito.when(requestHeadersUriSpecMock.uri(Mockito.anyString())).thenReturn(requestHeadersSpecMock);
        Mockito.when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToMono(com.eri.swagger.movie_api.model.Movie.class)).thenReturn(Mono.just(movieExpected));
        Movie movieActual = movieManagerService.findMovieById(1);
        Assert.assertEquals(movieExpected.getId().intValue(), movieActual.getId());
        Assert.assertEquals(movieExpected.getTitle(), movieActual.getTitle());
        Assert.assertEquals(movieExpected.getCategories().size(), movieActual.getCategories().size());
        Assert.assertEquals(movieExpected.getCategories().get(0), movieActual.getCategories().get(0));
        Assert.assertEquals(movieExpected.getCategories().get(1), movieActual.getCategories().get(1));
        Assert.assertEquals(movieExpected.getDirectors().size(), movieActual.getDirectors().size());
        Assert.assertEquals(movieExpected.getDirectors().get(0).getName(), movieActual.getDirectors().get(0).getName());
        Assert.assertEquals(movieExpected.getDirectors().get(0).getSurname(), movieActual.getDirectors().get(0).getSurname());
        Assert.assertEquals(movieExpected.getStars().size(), movieActual.getStars().size());
        Assert.assertEquals(movieExpected.getStars().get(0).getName(), movieActual.getStars().get(0).getName());
        Assert.assertEquals(movieExpected.getStars().get(0).getSurname(), movieActual.getStars().get(0).getSurname());
    }

    @Test
    public void findMovieByIdMovieNotFoundTest(){
        Mockito.when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        Mockito.when(requestHeadersUriSpecMock.uri(Mockito.anyString())).thenReturn(requestHeadersSpecMock);
        Mockito.when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToMono(com.eri.swagger.movie_api.model.Movie.class)).thenReturn(Mono.empty());
        Movie movieActual = movieManagerService.findMovieById(1200);
        Assert.assertNull(movieActual);
    }
    //endregion findMovieById

    //region addMovie
    @Test
    public void addMovieTest(){
        Movie movie = dataHelper.getExpectedMovie();
        Mockito.when(webClientMock.post()).thenReturn(requestBodyUriSpecMock);
        Mockito.when(requestBodyUriSpecMock.uri(Mockito.anyString())).thenReturn(requestBodySpecMock);
        Mockito.when(requestBodySpecMock.header(Mockito.eq(HttpHeaders.CONTENT_TYPE), Mockito.eq(MediaType.APPLICATION_JSON_VALUE))).thenReturn(requestBodySpecMock);
        Mockito.when(requestBodySpecMock.body(Mockito.any(Mono.class), Mockito.eq(com.eri.swagger.movie_api.model.Movie.class))).thenReturn(requestHeadersSpecMock);
        Mockito.when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToMono(Mockito.eq(Void.class))).thenReturn(Mono.empty());
        movieManagerService.addMovie(movie);
    }
    //endregion addMovie

    //region removeMovieById
    @Test
    public void removeMovieByIdTest(){
        Mockito.when(webClientMock.delete()).thenReturn(requestHeadersUriSpecMock);
        Mockito.when(requestHeadersUriSpecMock.uri(Mockito.anyString())).thenReturn(requestHeadersSpecMock);
        Mockito.when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToMono(Mockito.eq(Void.class))).thenReturn(Mono.empty());
        movieManagerService.removeMovieById(1);
    }
    //endregion removeMovieById
}
