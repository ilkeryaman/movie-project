package com.eri.model.messaging;

import java.util.UUID;

public class EventMessage {
    private String key;
    private String topic;
    private Object payload;

    public EventMessage(){}

    public EventMessage(String topic, Object payload){
        this. key = UUID.randomUUID().toString();
        this.topic = topic;
        this.payload = payload;
    }

    public EventMessage(String key, String topic, Object payload){
        this.key = key;
        this.topic = topic;
        this.payload = payload;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
