package com.task.currency.gateway.service;

import com.task.currency.gateway.dto.xml.CurrencyCommandDto;
import com.task.currency.gateway.dto.CurrencyRateDto;
import com.task.currency.gateway.dto.RateRequestDto;
import com.task.currency.gateway.dto.json.RateTimeRangeRequestDto;
import com.task.currency.gateway.entity.BaseRequest;
import com.task.currency.gateway.entity.RequestHistory;
import com.task.currency.gateway.exception.DuplicateIdException;
import com.task.currency.gateway.repository.BaseRequestRepository;
import com.task.currency.gateway.util.converter.CurrencyBaseConverter;
import com.task.currency.gateway.util.mapper.CurrencyObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private BaseRequestRepository baseRequestRepository;
    private StatisticCollectorService statisticCollectorService;
    private KafkaTemplate<String, String> template;

    @Override
    public void createBaseRequest(CurrencyRateDto currencyRateDto) {
        BaseRequest baseRequest = CurrencyObjectMapper.mapResponseToBaseRequest(currencyRateDto);
        baseRequestRepository.save(baseRequest);
    }

    @Override
    public List<CurrencyRateDto> findCurrencyRatesByTimeRange(RateTimeRangeRequestDto rateTimeRangeRequestDto) {
        validateRequestId(rateTimeRangeRequestDto.getRequestId());
        statisticCollectorService.createHistoryRecord(rateTimeRangeRequestDto);

        List<BaseRequest> byTimestampBetween = baseRequestRepository.findByTimestampBetween(
                LocalDateTime.now().minusHours(rateTimeRangeRequestDto.getPeriod()),
                LocalDateTime.now());
        return byTimestampBetween.stream()
                .map(CurrencyObjectMapper::mapBaseRequestToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CurrencyRateDto findLatestCurrencyRate(RateRequestDto rateRequestDto) {
        validateRequestId(rateRequestDto.getRequestId());
        statisticCollectorService.createHistoryRecord(rateRequestDto);

        Optional<BaseRequest> firstByOrderByRateDateDesc = baseRequestRepository.findFirstByOrderByRateDateDesc();
        if (firstByOrderByRateDateDesc.isPresent()) {
            CurrencyRateDto currencyRateDto = CurrencyObjectMapper.mapBaseRequestToResponse(firstByOrderByRateDateDesc.get());
            CurrencyBaseConverter.convertCurrencyBase(currencyRateDto, rateRequestDto.getCurrency());
            return currencyRateDto;
        }
        return null;
    }

    @Override
    public List<CurrencyRateDto> findCurrencyRatesByTimeRange(CurrencyCommandDto currencyCommandDto) {
        String requestId = String.valueOf(currencyCommandDto.getHistory().getConsumer());
        validateRequestId(requestId);
        statisticCollectorService.createHistoryRecord(currencyCommandDto);

        List<BaseRequest> byTimestampBetween = baseRequestRepository.findByTimestampBetween(
                LocalDateTime.now().minusHours(currencyCommandDto.getHistory().getPeriod()),
                LocalDateTime.now());
        return byTimestampBetween.stream()
                .map(CurrencyObjectMapper::mapBaseRequestToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CurrencyRateDto findLatestCurrencyRate(CurrencyCommandDto currencyCommandDto) {
        validateRequestId(currencyCommandDto.getId());
        statisticCollectorService.createHistoryRecord(currencyCommandDto);

        Optional<BaseRequest> firstByOrderByRateDateDesc = baseRequestRepository.findFirstByOrderByRateDateDesc();

        if (firstByOrderByRateDateDesc.isPresent()) {
            CurrencyRateDto currencyRateDto = CurrencyObjectMapper.mapBaseRequestToResponse(firstByOrderByRateDateDesc.get());
            CurrencyBaseConverter.convertCurrencyBase(currencyRateDto, currencyCommandDto.getDetails().getCurrency());
            return currencyRateDto;
        }
        return null;
    }

    private void validateRequestId(String requestId) {
        Optional<RequestHistory> byId = statisticCollectorService.findRequestById(requestId);
        if (byId.isPresent()) {
            throw new DuplicateIdException();
        }
    }
}
