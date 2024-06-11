package com.task.currency.gateway.controller;

import com.task.currency.gateway.dto.CurrencyRateDto;
import com.task.currency.gateway.dto.RateRequestDto;
import com.task.currency.gateway.dto.json.RateTimeRangeRequestDto;
import com.task.currency.gateway.dto.xml.CurrencyCommandDto;
import com.task.currency.gateway.dto.xml.CurrencyHistoryDto;
import com.task.currency.gateway.service.ExchangeRateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class ExchangeRateController {

    private ExchangeRateService exchangeRateService;

    @PostMapping(path = "/json_api/current", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CurrencyRateDto> findCurrent(@RequestBody RateRequestDto rateRequestDto) {
        CurrencyRateDto latestCurrencyRate = exchangeRateService.findLatestCurrencyRate(rateRequestDto);
        if (latestCurrencyRate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity<>(latestCurrencyRate, HttpStatus.OK);
        }
    }

    @PostMapping(path = "/json_api/history", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CurrencyRateDto>> findPeriod(@RequestBody RateTimeRangeRequestDto rateTimeRangeRequestDto) {
        List<CurrencyRateDto> currencyRatesByTimeRange = exchangeRateService.findCurrencyRatesByTimeRange(rateTimeRangeRequestDto);
        return new ResponseEntity<>(currencyRatesByTimeRange, HttpStatus.OK);
    }

    @PostMapping(value = "xml_api/command", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> processCommand(@RequestBody CurrencyCommandDto commandDto) {
        if (commandDto.getDetails() != null) {
            CurrencyRateDto latestCurrencyRate = exchangeRateService.findLatestCurrencyRate(commandDto);
            return new ResponseEntity<>(latestCurrencyRate, HttpStatus.OK);
        } else if (commandDto.getHistory() != null) {
            List<CurrencyRateDto> currencyRatesByTimeRange = exchangeRateService.findCurrencyRatesByTimeRange(commandDto);
            return new ResponseEntity<>(new CurrencyHistoryDto(currencyRatesByTimeRange), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("<response>Unknown command</response>", HttpStatus.BAD_REQUEST);
        }
    }
}
