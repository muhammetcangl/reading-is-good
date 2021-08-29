package com.mcg.readingisgood.statistic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatisticDTO {

    private String month;
    private Long totalOrderCount;
    private Long totalBookCount;
    private Double totalAmount;
}
