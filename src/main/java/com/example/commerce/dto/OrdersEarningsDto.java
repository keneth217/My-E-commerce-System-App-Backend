package com.example.commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrdersEarningsDto {
    //orders btw dates
    private Long totalOrders;
    private Float totalEarnings;
    private List<OrderDto> orders;

}
