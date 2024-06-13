package com.task.currency.gateway.util.converter;

import com.task.currency.gateway.dto.CurrencyRateDto;
import com.task.currency.gateway.dto.RateRequestDto;
import com.task.currency.gateway.exception.NoSuchCurrencyException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class CurrencyBaseConverter {
    public static void convertCurrencyBase(CurrencyRateDto currencyRateDto, String baseCurrency) {
        Map<String, Double> originalRates = currencyRateDto.getRates();

        Double newBaseCurrency = originalRates.get(baseCurrency);
        if (newBaseCurrency == null) {
            throw new NoSuchCurrencyException();
        }

        if (currencyRateDto.getBase().equals(baseCurrency)) {
            return;
        }

        currencyRateDto.setBase(baseCurrency);

        for (Map.Entry<String, Double> entry : originalRates.entrySet()) {
            String currency = entry.getKey();
            Double rate = entry.getValue();
            Double convertedRate = new BigDecimal(rate / newBaseCurrency).setScale(6, RoundingMode.HALF_UP).doubleValue();
            currencyRateDto.getRates().put(currency, convertedRate);
        }
    }
}
