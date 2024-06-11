package com.task.currency.gateway.dto.json;

import com.task.currency.gateway.dto.RateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RateTimeRangeRequestDto extends RateRequestDto {
    private int period;
}
