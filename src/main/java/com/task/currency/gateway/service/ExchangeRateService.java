package com.task.currency.gateway.service;

import com.task.currency.gateway.dto.xml.CurrencyCommandDto;
import com.task.currency.gateway.dto.CurrencyRateDto;
import com.task.currency.gateway.dto.RateRequestDto;
import com.task.currency.gateway.dto.json.RateTimeRangeRequestDto;

import java.util.List;

public interface ExchangeRateService {

    void createBaseRequest(CurrencyRateDto currencyRateDto);

    List<CurrencyRateDto> findCurrencyRatesByTimeRange(RateTimeRangeRequestDto rateTimeRangeRequestDto);

    CurrencyRateDto findLatestCurrencyRate(RateRequestDto rateRequestDto);

    List<CurrencyRateDto> findCurrencyRatesByTimeRange(CurrencyCommandDto currencyCommandDto);

    CurrencyRateDto findLatestCurrencyRate(CurrencyCommandDto currencyCommandDto);
}
