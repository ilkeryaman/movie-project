package com.eri.service.impl;

import com.eri.constant.enums.CacheKey;
import com.eri.converter.mapstruct.IMovieRestMapper;
import com.eri.exception.CacheNotInitializedException;
import com.eri.helper.MovieDataHelper;
import com.eri.helper.MovieRestDataHelper;
import com.eri.model.Movie;
import com.eri.service.cache.ICacheService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MovieManagerRestServiceRestTemplateImplTest {
    @InjectMocks
    private MovieManagerRestServiceRestTemplateImpl movieManagerRestServiceRestTemplate;

    /*
    // I do not know why I have to use a captor to mock RestTemplate.exchange
    // but without it, Mockito is not mocking RestTemplate.exchange.
    */
    @Captor
    private ArgumentCaptor<Object> argumentCaptor;

    //region mocks
    @Mock
    private ICacheService cacheServiceMock;

    @Mock(name = "movieRestTemplate")
    private RestTemplate movieRestTemplateMock;

    @Mock
    private IMovieRestMapper movieRestMapper;
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
        ResponseEntity<com.eri.swagger.movie_api.model.Movie[]> responseEntity = new ResponseEntity<com.eri.swagger.movie_api.model.Movie[]>
                (movieRestList.toArray(com.eri.swagger.movie_api.model.Movie[]::new), HttpStatus.OK);
        // mocking
        Mockito.when(movieRestTemplateMock.exchange((String) argumentCaptor.capture(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(com.eri.swagger.movie_api.model.Movie[].class))).thenReturn(responseEntity);
        Mockito.when(movieRestMapper.generatedToModel(Mockito.eq(movieRestList.get(0)))).thenReturn(movieList.get(0));
        Mockito.when(movieRestMapper.generatedToModel(Mockito.eq(movieRestList.get(1)))).thenReturn(movieList.get(1));
        // actual method call
        List<Movie> moviesActual = movieManagerRestServiceRestTemplate.getMovies(fromCache);
        // assertions
        Assert.assertTrue(movieList.size() == moviesActual.size());
        Assert.assertEquals(movieList.get(0).getId(), moviesActual.get(0).getId());
        Assert.assertEquals(movieList.get(0).getTitle(), moviesActual.get(0).getTitle());
        Assert.assertTrue(movieList.get(0).getCategories().size() == moviesActual.get(0).getCategories().size());
        Assert.assertEquals(movieList.get(0).getCategories().get(0), moviesActual.get(0).getCategories().get(0));
        Assert.assertEquals(movieList.get(0).getCategories().get(1), moviesActual.get(0).getCategories().get(1));
        Assert.assertTrue(movieList.get(0).getDirectors().size() == moviesActual.get(0).getDirectors().size());
        Assert.assertEquals(movieList.get(0).getDirectors().get(0).getName(), moviesActual.get(0).getDirectors().get(0).getName());
        Assert.assertEquals(movieList.get(0).getDirectors().get(0).getSurname(), moviesActual.get(0).getDirectors().get(0).getSurname());
        Assert.assertTrue(movieList.get(0).getStars().size() == moviesActual.get(0).getStars().size());
        Assert.assertEquals(movieList.get(0).getStars().get(0).getName(), moviesActual.get(0).getStars().get(0).getName());
        Assert.assertEquals(movieList.get(0).getStars().get(0).getSurname(), moviesActual.get(0).getStars().get(0).getSurname());
        Assert.assertEquals(movieList.get(1).getId(), moviesActual.get(1).getId());
        Assert.assertEquals(movieList.get(1).getTitle(), moviesActual.get(1).getTitle());
        Assert.assertTrue(movieList.get(1).getCategories().size() == moviesActual.get(1).getCategories().size());
        Assert.assertEquals(movieList.get(1).getCategories().get(0), moviesActual.get(1).getCategories().get(0));
        Assert.assertEquals(movieList.get(1).getCategories().get(1), moviesActual.get(1).getCategories().get(1));
        Assert.assertEquals(movieList.get(1).getCategories().get(2), moviesActual.get(1).getCategories().get(2));
        Assert.assertTrue(movieList.get(1).getDirectors().size() == moviesActual.get(1).getDirectors().size());
        Assert.assertEquals(movieList.get(1).getDirectors().get(0).getName(), moviesActual.get(1).getDirectors().get(0).getName());
        Assert.assertEquals(movieList.get(1).getDirectors().get(0).getSurname(), moviesActual.get(1).getDirectors().get(0).getSurname());
        Assert.assertTrue(movieList.get(1).getStars().size() == moviesActual.get(1).getStars().size());
        Assert.assertEquals(movieList.get(1).getStars().get(0).getName(), moviesActual.get(1).getStars().get(0).getName());
        Assert.assertEquals(movieList.get(1).getStars().get(0).getSurname(), moviesActual.get(1).getStars().get(0).getSurname());
    }

    @Test
    public void getMoviesWithCacheTest(){
        boolean fromCache = true;
        // mocking
        Mockito.doReturn(movieList).when(cacheServiceMock).findListFromCacheWithKey(CacheKey.MOVIES.getName());
        // actual method call
        List<Movie> moviesActual = movieManagerRestServiceRestTemplate.getMovies(fromCache);
        // assertions
        Assert.assertEquals(movieList, moviesActual);
    }

    @Test(expected = CacheNotInitializedException.class)
    public void getMoviesWithNullCacheTest(){
        boolean fromCache = true;
        // mocking
        Mockito.when(cacheServiceMock.findListFromCacheWithKey(CacheKey.MOVIES.getName())).thenReturn(null);
        // actual method call
        movieManagerRestServiceRestTemplate.getMovies(fromCache);
    }
    //endregion getMovies

    //region findMovieById
    @Test
    public void findMovieByIdTest(){
        com.eri.swagger.movie_api.model.Movie movie = restDataHelper.getMovie();
        ResponseEntity<com.eri.swagger.movie_api.model.Movie> responseEntity =
                new ResponseEntity<com.eri.swagger.movie_api.model.Movie>(movie, HttpStatus.OK);
        // mocking
        Mockito
                .when(movieRestTemplateMock.exchange((String) argumentCaptor.capture(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(com.eri.swagger.movie_api.model.Movie.class), Mockito.anyInt()))
                .thenReturn(responseEntity);
        Mockito.when(movieRestMapper.generatedToModel(Mockito.eq(movie))).thenReturn(movieList.get(0));
        // actual method call
        Movie movieActual = movieManagerRestServiceRestTemplate.findMovieById(1);
        // assertions
        Assert.assertEquals(movie.getId().intValue(), movieActual.getId());
        Assert.assertEquals(movie.getTitle(), movieActual.getTitle());
        Assert.assertEquals(movie.getCategories().size(), movieActual.getCategories().size());
        Assert.assertEquals(movie.getCategories().get(0), movieActual.getCategories().get(0));
        Assert.assertEquals(movie.getCategories().get(1), movieActual.getCategories().get(1));
        Assert.assertEquals(movie.getDirectors().size(), movieActual.getDirectors().size());
        Assert.assertEquals(movie.getDirectors().get(0).getName(), movieActual.getDirectors().get(0).getName());
        Assert.assertEquals(movie.getDirectors().get(0).getSurname(), movieActual.getDirectors().get(0).getSurname());
        Assert.assertEquals(movie.getStars().size(), movieActual.getStars().size());
        Assert.assertEquals(movie.getStars().get(0).getName(), movieActual.getStars().get(0).getName());
        Assert.assertEquals(movie.getStars().get(0).getSurname(), movieActual.getStars().get(0).getSurname());
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void findMovieByIdMovieNotFoundExceptionTest(){
        // mocking
        Mockito
                .when(movieRestTemplateMock.exchange((String) argumentCaptor.capture(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), Mockito.eq(com.eri.swagger.movie_api.model.Movie.class), Mockito.anyInt()))
                .thenThrow(HttpClientErrorException.NotFound.class);
        // actual method call
        movieManagerRestServiceRestTemplate.findMovieById(1200);
    }
    //endregion findMovieById

    //region addMovie
    @Test
    public void addMovieTest(){
        Movie movie = dataHelper.createRandomMovie(3);
        ResponseEntity<com.eri.swagger.movie_api.model.Movie> responseEntity = new ResponseEntity<com.eri.swagger.movie_api.model.Movie>(HttpStatus.CREATED);
        // mocking
        Mockito
                .when(movieRestTemplateMock.exchange((String) argumentCaptor.capture(), Mockito.eq(HttpMethod.POST), Mockito.any(HttpEntity.class), Mockito.eq(com.eri.swagger.movie_api.model.Movie.class)))
                .thenReturn(responseEntity);
        // actual method call
        movieManagerRestServiceRestTemplate.addMovie(movie);
    }

    @Test(expected = HttpClientErrorException.BadRequest.class)
    public void addMovieBadRequestExceptionTest(){
        Movie movie = dataHelper.createRandomMovie(3);
        // mocking
        Mockito
                .doThrow(HttpClientErrorException.BadRequest.class)
                .when(movieRestTemplateMock)
                .exchange((String) argumentCaptor.capture(), Mockito.eq(HttpMethod.POST), Mockito.any(HttpEntity.class), Mockito.eq(com.eri.swagger.movie_api.model.Movie.class));
        // actual method call
        movieManagerRestServiceRestTemplate.addMovie(movie);
    }
    //endregion addMovie

    //region removeMovieById
    @Test
    public void removeMovieByIdTest(){
        ResponseEntity<com.eri.swagger.movie_api.model.Movie> responseEntity =
                new ResponseEntity<com.eri.swagger.movie_api.model.Movie>(HttpStatus.NO_CONTENT);
        // mocking
        Mockito
                .doReturn(responseEntity)
                .when(movieRestTemplateMock)
                .exchange((String) argumentCaptor.capture(), Mockito.eq(HttpMethod.DELETE), Mockito.any(HttpEntity.class), Mockito.eq(Void.class), Mockito.anyInt());
        // actual method call
        movieManagerRestServiceRestTemplate.removeMovieById(1);
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void removeMovieByIdMovieNotFoundExceptionTest(){
        // mocking
        Mockito
                .when(movieRestTemplateMock.exchange((String) argumentCaptor.capture(), Mockito.eq(HttpMethod.DELETE), Mockito.any(HttpEntity.class), Mockito.eq(Void.class), Mockito.anyInt()))
                .thenThrow(HttpClientErrorException.NotFound.class);
        // actual method call
        movieManagerRestServiceRestTemplate.removeMovieById(1200);
    }
    //endregion removeMovieById
}
