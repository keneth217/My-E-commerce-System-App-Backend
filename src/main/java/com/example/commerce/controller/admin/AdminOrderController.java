package com.example.commerce.controller.admin;

import com.example.commerce.dto.AnalyticResponseDto;
import com.example.commerce.dto.OrderDto;
import com.example.commerce.dto.OrdersEarningsDto;
import com.example.commerce.services.Admin.orders.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderService orderService;
    @GetMapping("/order/count")
    public long getOrderCount() {
        return orderService.getOrderCount();
    }

    @GetMapping("/placedOrders")
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping("/status/{orderId}/{orderStatus}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long orderId, @PathVariable String orderStatus){
        OrderDto orderDto=orderService.changeOrderStatus(orderId, orderStatus);
        if (orderDto==null){
            return new ResponseEntity<>("something went wrong", HttpStatus.BAD_REQUEST);

        }
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @GetMapping("/analytics")
    public ResponseEntity<AnalyticResponseDto> getAdminAnalytics() {
        try {
            AnalyticResponseDto analytics = orderService.adminAnalytics();
            return new ResponseEntity<>(analytics, HttpStatus.OK);
        } catch (Exception e) {
            // Log the error if necessary
            System.err.println("Error fetching analytics: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
   @GetMapping("/analytics/date")
    public ResponseEntity<OrdersEarningsDto> getOrdersAndEarningsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        
        try {
            OrdersEarningsDto orderEarningsDto = orderService.getOrdersAndEarningsBetweenDates(startDate, endDate);
            return new ResponseEntity<>(orderEarningsDto, HttpStatus.OK);
        } catch (Exception e) {
            // Log the error if necessary
            System.err.println("Error fetching orders and earnings: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
