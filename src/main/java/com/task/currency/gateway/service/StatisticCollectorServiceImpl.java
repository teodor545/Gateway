package com.task.currency.gateway.service;

import com.task.currency.gateway.config.KafkaConfig;
import com.task.currency.gateway.dto.xml.CurrencyCommandDto;
import com.task.currency.gateway.dto.RateRequestDto;
import com.task.currency.gateway.entity.RequestHistory;
import com.task.currency.gateway.kafka.KafkaMessageConverter;
import com.task.currency.gateway.repository.RequestHistoryRepository;
import com.task.currency.gateway.scheduled.RatesCollector;
import com.task.currency.gateway.util.mapper.RequestToHistoryRecordMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class StatisticCollectorServiceImpl implements StatisticCollectorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticCollectorServiceImpl.class);

    private RequestHistoryRepository requestHistoryRepository;
    private final KafkaTemplate<String, String> template;
    private final KafkaMessageConverter kafkaMessageConverter;
    private final KafkaConfig kafkaConfig;

    @Override
    public void createHistoryRecord(RateRequestDto rateRequestDto) {
        RequestHistory requestHistory = RequestToHistoryRecordMapper.jsonReqToHistoryRecord(rateRequestDto, "EXT_SERVICE_2");
        requestHistoryRepository.save(requestHistory);
        sendRequestLogToKafka(requestHistory);
    }

    @Override
    public void createHistoryRecord(CurrencyCommandDto currencyCommandDto) {
        RequestHistory requestHistory = RequestToHistoryRecordMapper.xmlReqToHistoryRecord(currencyCommandDto, "EXT_SERVICE_1");
        requestHistoryRepository.save(requestHistory);
        sendRequestLogToKafka(requestHistory);
    }

    @Override
    public Optional<RequestHistory> findRequestById(String requestId) {
        return requestHistoryRepository.findById(requestId);
    }

    private void sendRequestLogToKafka(Object dto) {
        try {
            template.send(kafkaConfig.getRequestTopicName(), kafkaMessageConverter.convertToKafkaMessage(dto));
        } catch (Exception e) {
            LOGGER.warn("Failed to send collector log to kafka", e);
        }
    }

}
