package com.task.currency.gateway.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "currencyRate")
public class CurrencyRateDto {

    @JacksonXmlProperty(localName = "timestamp")
    private long timestamp;

    @JacksonXmlProperty(localName = "base")
    private String base;

    @JacksonXmlProperty(localName = "date")
    private LocalDate date;

    @JacksonXmlElementWrapper(localName = "rates")
    @JacksonXmlProperty(localName = "rate")
    private Map<String, Double> rates;
}