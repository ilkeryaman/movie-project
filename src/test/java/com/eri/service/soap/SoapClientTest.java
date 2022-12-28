package com.eri.service.soap;

import com.eri.constant.enums.Category;
import com.eri.constants.enums.TestData;
import com.eri.generated.movieapi.stub.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.math.BigInteger;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class SoapClientTest {
    @InjectMocks
    private SoapClient soapClient = Mockito.spy(new SoapClient());

    //region mocks
    @Mock
    private WebServiceTemplate webServiceTemplate;
    //endregion mocks

    //region fields
    private String url = "http://localhost:8085/movie-api/movies";
    //endregion fields

    //region listMovies
    @Test
    public void listMoviesTest(){
        ListMoviesRequest request = new ListMoviesRequest();
        ListMoviesResponse response = new ListMoviesResponse();
        // mocking
        Mockito.when(soapClient.getWebServiceTemplate()).thenReturn(webServiceTemplate);
        Mockito.when(webServiceTemplate.marshalSendAndReceive(Mockito.eq(url), Mockito.any(ListMoviesRequest.class))).thenReturn(response);
        // actual method call
        ListMoviesResponse responseActual = soapClient.listMovies(url, request);
        // assertions
        Assert.assertEquals(response, responseActual);
    }
    //endregion listMovies

    //region addMovie
    @Test
    public void addMovie(){
        int id = 3;
        Director director = new Director();
        director.setName(TestData.DIRECTOR_NAME.getValue());
        director.setSurname(TestData.DIRECTOR_SURNAME.getValue());
        Star star = new Star();
        star.setName(TestData.STAR_NAME.getValue());
        star.setSurname(TestData.STAR_SURNAME.getValue());
        Movie movie = new Movie();
        movie.setId(BigInteger.valueOf(id));
        movie.setTitle(TestData.MOVIE_NAME.getValue() + " " + id);
        movie.getCategories().addAll(Arrays.asList(Category.ACTION.getName(), Category.DRAMA.getName()));
        movie.getDirectors().addAll(Arrays.asList(director));
        movie.getStars().addAll(Arrays.asList(star));
        AddMovieRequest request = new AddMovieRequest();
        request.setMovie(movie);
        // mocking
        Mockito.when(soapClient.getWebServiceTemplate()).thenReturn(webServiceTemplate);
        Mockito.when(webServiceTemplate.marshalSendAndReceive(Mockito.eq(url), Mockito.any(AddMovieRequest.class))).thenReturn(null);
        // actual method call
        soapClient.addMovie(url, request);
    }
    //endregion addMovie

    //region deleteMovie
    @Test
    public void deleteMovie(){
        DeleteMovieRequest request = new DeleteMovieRequest();
        request.setId(BigInteger.valueOf(3));
        // mocking
        Mockito.when(soapClient.getWebServiceTemplate()).thenReturn(webServiceTemplate);
        Mockito.when(webServiceTemplate.marshalSendAndReceive(Mockito.eq(url), Mockito.any(DeleteMovieRequest.class))).thenReturn(null);
        // actual method call
        soapClient.deleteMovie(url, request);
    }
    //endregion deleteMovie
}
