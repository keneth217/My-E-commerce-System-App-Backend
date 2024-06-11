package com.example.commerce.services.Admin.orders;

import com.example.commerce.dto.AnalyticResponseDto;
import com.example.commerce.dto.OrderDto;
import com.example.commerce.dto.OrdersEarningsDto;

import java.util.Date;
import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders();
    OrderDto changeOrderStatus(Long orderId,String status);
    AnalyticResponseDto adminAnalytics();
    OrdersEarningsDto getOrdersAndEarningsBetweenDates(Date startDate, Date endDate);
    public long getOrderCount();
}
