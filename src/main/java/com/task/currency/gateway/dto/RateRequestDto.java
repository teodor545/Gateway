package com.task.currency.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RateRequestDto {
    private String requestId;
    private long timestamp;
    private long client;
    private String currency;
}
