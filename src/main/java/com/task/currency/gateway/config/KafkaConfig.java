package com.task.currency.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Getter
@Setter
@Configuration
public class KafkaConfig {
    @Value("${gateway.kafka.request.topic.partitions}")
    private int requestTopicPartitions;
    @Value("${gateway.kafka.collector.topic.partitions}")
    private int collectorTopicPartitions;
    @Value("${gateway.kafka.request.topic.name}")
    private String requestTopicName;
    @Value("${gateway.kafka.collector.topic.name}")
    private String collectorTopicName;

    @Bean
    public NewTopic createRequestLogTopic() {
        return TopicBuilder.name(requestTopicName)
                .partitions(requestTopicPartitions)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic createCollectorLogTopic() {
        return TopicBuilder.name(requestTopicName)
                .partitions(collectorTopicPartitions)
                .replicas(1)
                .build();
    }
}
