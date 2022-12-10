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
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    private ICacheService cacheService;

    @MockBean(name = "movieRestTemplate")
    private RestTemplate movieRestTemplate;

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
                .when(movieRestTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class)))
                .thenReturn(responseEntity);
        List<Movie> moviesResponse = movieManagerService.getMovies(fromCache);
        Assert.assertEquals(moviesExpected.size(), moviesResponse.size());
        Assert.assertEquals(moviesExpected.get(0).getId(), moviesResponse.get(0).getId());
        Assert.assertEquals(moviesExpected.get(0).getTitle(), moviesResponse.get(0).getTitle());
        Assert.assertEquals(moviesExpected.get(0).getCategories().size(), moviesResponse.get(0).getCategories().size());
        Assert.assertEquals(moviesExpected.get(0).getCategories().get(0), moviesResponse.get(0).getCategories().get(0));
        Assert.assertEquals(moviesExpected.get(0).getCategories().get(1), moviesResponse.get(0).getCategories().get(1));
        Assert.assertEquals(moviesExpected.get(0).getDirectors().size(), moviesResponse.get(0).getDirectors().size());
        Assert.assertEquals(moviesExpected.get(0).getDirectors().get(0).getName(), moviesResponse.get(0).getDirectors().get(0).getName());
        Assert.assertEquals(moviesExpected.get(0).getDirectors().get(0).getSurname(), moviesResponse.get(0).getDirectors().get(0).getSurname());
        Assert.assertEquals(moviesExpected.get(0).getStars().size(), moviesResponse.get(0).getStars().size());
        Assert.assertEquals(moviesExpected.get(0).getStars().get(0).getName(), moviesResponse.get(0).getStars().get(0).getName());
        Assert.assertEquals(moviesExpected.get(0).getStars().get(0).getSurname(), moviesResponse.get(0).getStars().get(0).getSurname());
    }

    @Test
    public void getMoviesWithCacheTest(){
        boolean fromCache = true;
        List<Movie> moviesExpected = dataHelper.getExpectedMovieList(fromCache);
        Mockito.doReturn(moviesExpected).when(cacheService).findListFromCacheWithKey(CacheKey.MOVIES.getName());
        List<Movie> moviesReturned = movieManagerService.getMovies(fromCache);
        Assert.assertEquals(moviesExpected, moviesReturned);
    }

    @Test(expected = CacheNotInitializedException.class)
    public void getMoviesWithNullCacheTest(){
        boolean fromCache = true;
        Mockito.when(cacheService.findListFromCacheWithKey(CacheKey.MOVIES.getName())).thenReturn(null);
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
                .when(movieRestTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class), Mockito.anyInt()))
                .thenReturn(responseEntity);
        Movie movieResponse = movieManagerService.findMovieById(110);
        Assert.assertEquals(movieExpected.getId(), movieResponse.getId());
        Assert.assertEquals(movieExpected.getTitle(), movieResponse.getTitle());
        Assert.assertEquals(movieExpected.getCategories().size(), movieResponse.getCategories().size());
        Assert.assertEquals(movieExpected.getCategories().get(0), movieResponse.getCategories().get(0));
        Assert.assertEquals(movieExpected.getCategories().get(1), movieResponse.getCategories().get(1));
        Assert.assertEquals(movieExpected.getDirectors().size(), movieResponse.getDirectors().size());
        Assert.assertEquals(movieExpected.getDirectors().get(0).getName(), movieResponse.getDirectors().get(0).getName());
        Assert.assertEquals(movieExpected.getDirectors().get(0).getSurname(), movieResponse.getDirectors().get(0).getSurname());
        Assert.assertEquals(movieExpected.getStars().size(), movieResponse.getStars().size());
        Assert.assertEquals(movieExpected.getStars().get(0).getName(), movieResponse.getStars().get(0).getName());
        Assert.assertEquals(movieExpected.getStars().get(0).getSurname(), movieResponse.getStars().get(0).getSurname());
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void findMovieByIdMovieNotFoundExceptionTest(){
        Mockito
                .when(movieRestTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class), Mockito.anyInt()))
                .thenThrow(HttpClientErrorException.NotFound.class);
        movieManagerService.findMovieById(110);
    }
    //endregion findMovieById

    //region addMovie
    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void addMovieBadRequestExceptionTest(){
        Movie movie = dataHelper.getExpectedMovie();
        Mockito
                .doThrow(HttpClientErrorException.BadRequest.class)
                .when(movieRestTemplate)
                .exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class));
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
                .when(movieRestTemplate)
                .exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class), Mockito.anyInt());
        movieManagerService.removeMovieById(1);
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void removeMovieByIdMovieNotFoundExceptionTest(){
        Mockito
                .when(movieRestTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(Class.class), Mockito.anyInt()))
                .thenThrow(HttpClientErrorException.NotFound.class);
        movieManagerService.removeMovieById(1);
    }
    //endregion removeMovieById
}
