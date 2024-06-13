package com.task.currency.gateway.util.mapper;

import com.task.currency.gateway.dto.xml.CurrencyCommandDto;
import com.task.currency.gateway.dto.RateRequestDto;
import com.task.currency.gateway.entity.RequestHistory;
import com.task.currency.gateway.util.Constants;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class RequestToHistoryRecordMapper {

    public static RequestHistory jsonReqToHistoryRecord(RateRequestDto rateRequestDto, String serviceName) {
        RequestHistory requestHistory = new RequestHistory();
        requestHistory.setRequestId(rateRequestDto.getRequestId());
        requestHistory.setCurrency(rateRequestDto.getCurrency());
        requestHistory.setServiceName(serviceName);
        requestHistory.setTimestamp(LocalDateTime.ofInstant(Instant.ofEpochSecond(rateRequestDto.getTimestamp()), Constants.APP_TIME_ZONE));
        requestHistory.setClientId(rateRequestDto.getClient());
        return requestHistory;
    }

    public static RequestHistory xmlReqToHistoryRecord(CurrencyCommandDto currencyCommandDto, String serviceName) {
        RequestHistory requestHistory = new RequestHistory();
        requestHistory.setRequestId(currencyCommandDto.getId());
        String currency;
        Long consumer;
        if (currencyCommandDto.getDetails() != null) {
            currency = currencyCommandDto.getDetails().getCurrency();
            consumer = currencyCommandDto.getDetails().getConsumer();
        } else {
            currency = currencyCommandDto.getHistory().getCurrency();
            consumer = currencyCommandDto.getHistory().getConsumer();
        }
        requestHistory.setCurrency(currency);
        requestHistory.setClientId(consumer);
        requestHistory.setServiceName(serviceName);
        requestHistory.setTimestamp(LocalDateTime.now());
        return requestHistory;
    }
}
