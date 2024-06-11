package com.example.commerce.controller.customer;

import com.example.commerce.dto.OrderDto;
import com.example.commerce.services.Customer.cart.CartServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class TrackingController {
    private final CartServices cartServices;
    public ResponseEntity<OrderDto> trackItems(@PathVariable UUID trackingId){
        OrderDto orderDto=cartServices.searchOrderByTrackingId(trackingId);
        if (orderDto == null) {
return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDto);
    }
}
