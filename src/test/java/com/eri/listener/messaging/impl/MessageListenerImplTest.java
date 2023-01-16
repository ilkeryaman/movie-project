package com.eri.listener.messaging.impl;

import com.eri.constant.enums.Topic;
import com.eri.exception.DeserializationException;
import com.eri.helper.MovieDataHelper;
import com.eri.model.Movie;
import com.eri.service.messaging.IMessageManagerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessageListenerImplTest {
    @InjectMocks
    private MessageListenerImpl messageListener;

    //region mocks
    @Mock
    private IMessageManagerService messageManagerServiceMock;

    @Mock
    private ObjectMapper objectMapperMock;
    //endregion mocks

    //region fields
    private MovieDataHelper dataHelper;
    //endregion fields

    @Before
    public void init(){
        dataHelper = new MovieDataHelper();
    }

    //region listenMovieDBTopics
    @Test
    public void listenMovieDBTopicsTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Movie movie = dataHelper.createRandomMovie(6);
        String movieAsString = null;
        try {
            movieAsString = objectMapper.writeValueAsString(movie);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        // mocking
        Mockito.when(objectMapperMock.readValue(Mockito.anyString(), Mockito.eq(Movie.class))).thenReturn(movie);
        Mockito.doNothing().when(messageManagerServiceMock).addMovieToNewcomersCache(Mockito.any(Movie.class));
        // actual method call
        messageListener.listenMovieDBTopics(Topic.MOVIEDB_MOVIE_CREATED.getName(), movieAsString);
        // assertions
        Mockito.verify(messageManagerServiceMock, Mockito.times(1)).addMovieToNewcomersCache(Mockito.any(Movie.class));
    }

    @Test(expected = DeserializationException.class)
    public void listenMovieDBTopicsJsonProcessingExceptionTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Movie movie = dataHelper.createRandomMovie(6);
        String movieAsString = null;
        try {
            movieAsString = objectMapper.writeValueAsString(movie);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        // mocking
        Mockito.when(objectMapperMock.readValue(Mockito.anyString(), Mockito.eq(Movie.class))).thenThrow(JsonProcessingException.class);
        // actual method call
        messageListener.listenMovieDBTopics(Topic.MOVIEDB_MOVIE_CREATED.getName(), movieAsString);
    }

    @Test
    public void listenMovieDBTopicsInvalidTopicTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Movie movie = dataHelper.createRandomMovie(6);
        String movieAsString = null;
        try {
            movieAsString = objectMapper.writeValueAsString(movie);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        // mocking
        Mockito.when(objectMapperMock.readValue(Mockito.anyString(), Mockito.eq(Movie.class))).thenReturn(movie);
        // actual method call
        messageListener.listenMovieDBTopics(Topic.MOVIEDB_MOVIE_CREATED.getName() + "_broken", movieAsString);
        // assertions
        Mockito.verify(messageManagerServiceMock, Mockito.never()).addMovieToNewcomersCache(Mockito.any(Movie.class));
    }
    //endregion listenMovieDBTopics
}
