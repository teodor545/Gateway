package com.task.currency.gateway.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "command")
public class CurrencyCommandDto {
    @JacksonXmlProperty(isAttribute = true)
    private String id;
    @JacksonXmlProperty(isAttribute = true, localName="get")
    private GetDetails details;
    @JacksonXmlProperty(localName="history")
    private HistoryDetails history;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class GetDetails {
        @JacksonXmlProperty(isAttribute = true)
        private Long consumer;
        private String currency;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class HistoryDetails {
        @JacksonXmlProperty(isAttribute = true)
        private Long consumer;
        @JacksonXmlProperty(isAttribute = true)
        private String currency;
        @JacksonXmlProperty(isAttribute = true)
        private Long period;
    }
}