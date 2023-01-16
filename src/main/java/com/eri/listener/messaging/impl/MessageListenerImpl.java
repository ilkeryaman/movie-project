package com.eri.listener.messaging.impl;

import com.eri.configuration.messaging.KafkaTopicConfig;
import com.eri.constant.enums.Topic;
import com.eri.exception.DeserializationException;
import com.eri.listener.messaging.MessageListener;
import com.eri.model.Movie;
import com.eri.service.messaging.IMessageManagerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Component
@ConditionalOnProperty(prefix = "spring.kafka", name = "enabled", matchIfMissing = true)
public class MessageListenerImpl implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageListenerImpl.class);

    @Resource
    private KafkaTopicConfig kafkaTopicConfig;

    @Resource
    private IMessageManagerService messageManagerService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "#{kafkaTopicConfig.getTopics()}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenMovieDBTopics(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Payload String message) {
        Movie movie = deSerializeMessage(message);

        if(Topic.MOVIEDB_MOVIE_CREATED.getName().equals(topic)){
            messageManagerService.addMovieToNewcomersCache(movie);
        }

        logger.info("Topic: [{}], Received message: {}", topic, message);
    }

    private Movie deSerializeMessage(String message){
        Movie movie = null;
        if(StringUtils.hasLength(message)){
            try {
                movie = objectMapper.readValue(message, Movie.class);
            } catch (JsonProcessingException ex) {
                throw new DeserializationException();
            }
        }
        return movie;
    }
}
