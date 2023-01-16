package com.eri.service.messaging.impl;

import com.eri.constant.enums.Topic;
import com.eri.exception.MessageNotProcessedError;
import com.eri.helper.MovieDataHelper;
import com.eri.model.Movie;
import com.eri.model.messaging.EventMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@RunWith(MockitoJUnitRunner.class)
public class KafkaServiceImplTest {
    @InjectMocks
    private KafkaServiceImpl kafkaService;

    //region mocks
    @Mock
    private KafkaTemplate<String, String> messageTemplateMock;

    @Spy
    private ObjectMapper objectMapper;
    //endregion mocks

    //region fields
    private MovieDataHelper dataHelper;
    //endregion fields

    @Before
    public void init(){
        dataHelper = new MovieDataHelper();
    }

    //region sendMessageTest
    @Test
    public void sendMessageTest() throws JsonProcessingException {
        ReflectionTestUtils.setField(kafkaService, "kafkaEnabled", true);
        Movie movie = dataHelper.createRandomMovie(6);
        int partition = 1;
        long offset = 1L;
        RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition(Topic.MOVIEDB_MOVIE_CREATED.getName(), partition), offset, 0L, 0L, 0L, 0, 0);
        // mocking
        SendResult<String, String> sendResultMock = Mockito.mock(SendResult.class);
        ListenableFuture<SendResult<String, String>> responseFutureMock = Mockito.mock(ListenableFuture.class);
        Mockito.when(objectMapper.writeValueAsString(Mockito.any(Movie.class))).thenCallRealMethod();
        Mockito.when(sendResultMock.getRecordMetadata()).thenReturn(recordMetadata);
        Mockito.when(messageTemplateMock.send(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(responseFutureMock);
        Mockito.doAnswer(invocationOnMock -> {
            ListenableFutureCallback listenableFutureCallback = invocationOnMock.getArgument(0);
            listenableFutureCallback.onSuccess(sendResultMock);
            // assertions
            Assert.assertEquals(sendResultMock.getRecordMetadata().offset(), offset);
            Assert.assertEquals(sendResultMock.getRecordMetadata().partition(), partition);
            return null;
        }).when(responseFutureMock).addCallback(Mockito.any(ListenableFutureCallback.class));
        // actual method call
        kafkaService.sendMessage(new EventMessage(Topic.MOVIEDB_MOVIE_CREATED.getName(), movie));
        // assertions
        Mockito.verify(messageTemplateMock, Mockito.times(1)).send(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test(expected = MessageNotProcessedError.class)
    public void sendMessageProcessingErrorTest() throws JsonProcessingException {
        ReflectionTestUtils.setField(kafkaService, "kafkaEnabled", true);
        Movie movie = dataHelper.createRandomMovie(6);
        // mocking
        Mockito.when(objectMapper.writeValueAsString(Mockito.any(Movie.class))).thenThrow(JsonProcessingException.class);
        // actual method call
        kafkaService.sendMessage(new EventMessage(Topic.MOVIEDB_MOVIE_CREATED.getName(), movie));
    }

    @Test
    public void sendMessageFailureTest() throws JsonProcessingException {
        ReflectionTestUtils.setField(kafkaService, "kafkaEnabled", true);
        Movie movie = dataHelper.createRandomMovie(6);
        String message = "An error occured!";
        // mocking
        Mockito.when(objectMapper.writeValueAsString(Mockito.any(Movie.class))).thenCallRealMethod();
        ListenableFuture<SendResult<String, String>> responseFutureMock = Mockito.mock(ListenableFuture.class);
        Throwable throwableMock = Mockito.mock(Throwable.class);
        Mockito.when(throwableMock.getMessage()).thenReturn(message);
        Mockito.when(messageTemplateMock.send(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(responseFutureMock);
        Mockito.doAnswer(invocationOnMock -> {
            ListenableFutureCallback listenableFutureCallback = invocationOnMock.getArgument(0);
            listenableFutureCallback.onFailure(throwableMock);
            return null;
        }).when(responseFutureMock).addCallback(Mockito.any(ListenableFutureCallback.class));
        // actual method call
        kafkaService.sendMessage(new EventMessage(Topic.MOVIEDB_MOVIE_CREATED.getName(), movie));
        // assertions
        Mockito.verify(messageTemplateMock, Mockito.times(1)).send(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void sendMessageKafkaDisabledTest(){
        ReflectionTestUtils.setField(kafkaService, "kafkaEnabled", false);
        Movie movie = dataHelper.createRandomMovie(6);
        // actual method call
        kafkaService.sendMessage(new EventMessage(Topic.MOVIEDB_MOVIE_CREATED.getName(), movie));
        // assertions
        Mockito.verify(messageTemplateMock, Mockito.never()).send(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
    //endregion sendMessageTest
}
