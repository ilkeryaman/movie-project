package com.eri.configuration.messaging;

import com.eri.constant.enums.Topic;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "spring.kafka", name = "enabled", matchIfMissing = true)
public class KafkaTopicConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic movieCreatedTopic() {
        return new NewTopic(Topic.MOVIEDB_MOVIE_CREATED.getName(), 1, (short) 1);
    }

    @Bean
    public String[] getTopics() {
        return new String[]{
                Topic.MOVIEDB_MOVIE_CREATED.getName()
        };
    }
}
