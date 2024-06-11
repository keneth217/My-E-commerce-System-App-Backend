package com.example.commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalyticResponseDto {
    private Long placed;
    private Long shipped;
    private Long delivered;
    private Float previousMontOrders;
    private Float currentMontOrders;
    private Float previousMonthEarnings;
    private Float currentMonthEarnings;

}
