package com.task.currency.gateway.scheduled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.currency.gateway.config.KafkaConfig;
import com.task.currency.gateway.dto.CurrencyRateDto;
import com.task.currency.gateway.kafka.KafkaMessageConverter;
import com.task.currency.gateway.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class RatesCollector {
    private static final Logger LOGGER = LoggerFactory.getLogger(RatesCollector.class);
    private static final String LOG_PREFIX = "[Scheduled data collecting] ";

    private final RestTemplate restTemplate;
    private final ExchangeRateService exchangeRateService;
    private final KafkaTemplate<String, String> template;
    private final KafkaMessageConverter kafkaMessageConverter;
    private final KafkaConfig kafkaConfig;

    @Value("${rates.collector.api.key}")
    private String apiKey;
    @Value("${rates.collector.latest.url}")
    private String ratesUrl;

    @Scheduled(fixedRateString = "${rates.collector.schedule.time}", initialDelayString = "${rates.collector.schedule.initial.delay}")
    private void collectLatestRates() {
        LOGGER.info(LOG_PREFIX +"Collecting currency rates");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ratesUrl)
					.queryParam("access_key", apiKey)
					.queryParam("format", "1");
	    ResponseEntity<CurrencyRateDto> responseEntity = restTemplate
				.getForEntity(builder.toUriString(), CurrencyRateDto.class);

        CurrencyRateDto responseEntityBody = responseEntity.getBody();
        if (responseEntityBody != null) {
            exchangeRateService.createBaseRequest(responseEntityBody);
            sendLogToKafka(responseEntityBody);
            LOGGER.info(LOG_PREFIX +"Finished persisting currency rates");
        } else {
            LOGGER.info(LOG_PREFIX +"Could not collect currency rates");
        }
    }

    private void sendLogToKafka(Object dto) {
        try {
            template.send(kafkaConfig.getCollectorTopicName(), kafkaMessageConverter.convertToKafkaMessage(dto));
        } catch (Exception e) {
            LOGGER.warn(LOG_PREFIX +"Failed to send collector log to kafka", e);
        }
    }
}
