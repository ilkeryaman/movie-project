package com.eri.service.impl;

import com.eri.base.MovieProjectJUnitTestBase;
import com.eri.constant.enums.CacheKey;
import com.eri.converter.mapstruct.IMovieRestMapper;
import com.eri.exception.CacheNotInitializedException;
import com.eri.helper.MovieDataHelper;
import com.eri.helper.MovieRestDataHelper;
import com.eri.model.Movie;
import com.eri.service.IMovieManagerService;
import com.eri.service.cache.ICacheService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class MovieManagerRestServiceRestTemplateImplTest extends MovieProjectJUnitTestBase {
    @Resource
    private MovieDataHelper dataHelper;

    @Resource
    private MovieRestDataHelper restDataHelper;

    @Resource(name = "movieManagerRestTemplateService")
    private IMovieManagerService movieManagerService;

    @Resource
    private IMovieRestMapper movieRestMapper;

    @MockBean
    private ICacheService cacheServiceMock;

    @MockBean(name = "movieRestTemplate")
    private RestTemplate movieRestTemplateMock;

    //region getMovies
    @Test
    public void getMoviesTest(){
        boolean fromCache = false;
        List<com.eri.swagger.movie_api.model.Movie> moviesFromRestService = restDataHelper.getExpectedMovieList(fromCache);
        List<Movie> moviesExpected = new ArrayList<>();
        moviesFromRestService.forEach(movie -> moviesExpected.add(movieRestMapper.generatedToModel(movie)));
        ResponseEntity<com.eri.swagger.movie_api.model.Movie[]> responseEntity = new ResponseEntity<com.eri.swagger.movie_api.model.Movie[]>
                (moviesFromRestService.toArray(com.eri.swagger.movie_api.model.Movie[]::new), HttpStatus.OK);
        Mockito
                .when(movieRestTemplateMock.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(com.eri.swagger.movie_api.model.Movie[].class)))
                .thenReturn(responseEntity);
        List<Movie> moviesActual = movieManagerService.getMovies(fromCache);
        Assert.assertEquals(moviesExpected.size(), moviesActual.size());
        Assert.assertEquals(moviesExpected.get(0).getId(), moviesActual.get(0).getId());
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
    }

    @Test
    public void getMoviesWithCacheTest(){
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
        com.eri.swagger.movie_api.model.Movie movie = restDataHelper.getExpectedMovie();
        Movie movieExpected = movieRestMapper.generatedToModel(movie);
        ResponseEntity<com.eri.swagger.movie_api.model.Movie> responseEntity =
                new ResponseEntity<com.eri.swagger.movie_api.model.Movie>(movie, HttpStatus.OK);
        Mockito
                .when(movieRestTemplateMock.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(com.eri.swagger.movie_api.model.Movie.class), Mockito.anyInt()))
                .thenReturn(responseEntity);
        Movie movieActual = movieManagerService.findMovieById(110);
        Assert.assertEquals(movieExpected.getId(), movieActual.getId());
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

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void findMovieByIdMovieNotFoundExceptionTest(){
        Mockito
                .when(movieRestTemplateMock.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(com.eri.swagger.movie_api.model.Movie.class), Mockito.anyInt()))
                .thenThrow(HttpClientErrorException.NotFound.class);
        movieManagerService.findMovieById(1200);
    }
    //endregion findMovieById

    //region addMovie
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void addMovieBadRequestExceptionTest(){
        Movie movie = dataHelper.getExpectedMovie();
        Mockito
                .doThrow(HttpClientErrorException.BadRequest.class)
                .when(movieRestTemplateMock)
                .exchange(Mockito.anyString(), Mockito.eq(HttpMethod.POST), Mockito.any(HttpEntity.class), Mockito.eq(com.eri.swagger.movie_api.model.Movie.class));
        movieManagerService.addMovie(movie);
    }
    //endregion addMovie

    //region removeMovieById
    @Test
    public void removeMovieByIdTest(){
        ResponseEntity<com.eri.swagger.movie_api.model.Movie> responseEntity =
                new ResponseEntity<com.eri.swagger.movie_api.model.Movie>(HttpStatus.NO_CONTENT);
        Mockito
                .doReturn(responseEntity)
                .when(movieRestTemplateMock)
                .exchange(Mockito.anyString(), Mockito.eq(HttpMethod.DELETE), Mockito.any(HttpEntity.class), Mockito.eq(Void.class), Mockito.anyInt());
        movieManagerService.removeMovieById(1);
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void removeMovieByIdMovieNotFoundExceptionTest(){
        Mockito
                .when(movieRestTemplateMock.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.DELETE), Mockito.any(HttpEntity.class), Mockito.eq(Void.class), Mockito.anyInt()))
                .thenThrow(HttpClientErrorException.NotFound.class);
        movieManagerService.removeMovieById(1200);
    }
    //endregion removeMovieById
}
