package com.task.currency.gateway.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.currency.gateway.scheduled.RatesCollector;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class KafkaMessageConverter {

    private ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessageConverter.class);
    public String convertToKafkaMessage(Object toConvert) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(toConvert);
        } catch (Exception e) {
            LOGGER.warn("Failed conversion to kafka message", e);
        }
        return null;
    }

}
