package com.eri.listener.messaging;

public interface MessageListener {
    void listenMovieDBTopics(String topic, String message);
}
