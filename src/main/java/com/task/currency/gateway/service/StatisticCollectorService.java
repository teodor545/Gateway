package com.task.currency.gateway.service;

import com.task.currency.gateway.dto.xml.CurrencyCommandDto;
import com.task.currency.gateway.dto.RateRequestDto;
import com.task.currency.gateway.entity.RequestHistory;

import java.util.Optional;

public interface StatisticCollectorService {

    void createHistoryRecord(RateRequestDto rateRequestDto);

    void createHistoryRecord(CurrencyCommandDto currencyCommandDto);

    Optional<RequestHistory> findRequestById(String requestId);
}
