package com.task.currency.gateway.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.task.currency.gateway.dto.CurrencyRateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "history")
public class CurrencyHistoryDto {

    @JacksonXmlProperty(localName = "currencies")
    private List<CurrencyRateDto> currencyRates;
}
