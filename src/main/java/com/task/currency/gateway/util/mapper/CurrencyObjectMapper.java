package com.task.currency.gateway.util.mapper;

import com.task.currency.gateway.dto.CurrencyRateDto;
import com.task.currency.gateway.entity.BaseRequest;
import com.task.currency.gateway.entity.ExchangeRate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyObjectMapper {

    private static final String DEFAULT_TIME_ZONE = "UTC";

    public static BaseRequest mapResponseToBaseRequest(CurrencyRateDto currencyRateDto) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setBase(currencyRateDto.getBase());
        baseRequest.setRateDate(currencyRateDto.getDate());
        baseRequest.setTimestamp(LocalDateTime.ofInstant(Instant.ofEpochSecond(currencyRateDto.getTimestamp()),ZoneId.of(DEFAULT_TIME_ZONE)));

        List<ExchangeRate> exchangeRates = new ArrayList<>();
        for (Map.Entry<String, Double> rate : currencyRateDto.getRates().entrySet()) {
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setCurrency(rate.getKey());
            exchangeRate.setRate(rate.getValue());
            exchangeRate.setRequest(baseRequest);
            exchangeRate.setRequest(baseRequest);
            exchangeRates.add(exchangeRate);
        }

        baseRequest.setExchangeRates(exchangeRates);
        return baseRequest;
    }

    public static CurrencyRateDto mapBaseRequestToResponse(BaseRequest baseRequest) {
        CurrencyRateDto currencyRateDto = new CurrencyRateDto();
        currencyRateDto.setBase(baseRequest.getBase());
        currencyRateDto.setDate(baseRequest.getRateDate());
        currencyRateDto.setTimestamp(baseRequest.getTimestamp().atZone(ZoneId.of(DEFAULT_TIME_ZONE)).toEpochSecond());

        Map<String,Double> rates = new HashMap<>();
        baseRequest.getExchangeRates().forEach(rate -> rates.put(rate.getCurrency(), rate.getRate()));
        currencyRateDto.setRates(rates);
        return currencyRateDto;
    }
}
