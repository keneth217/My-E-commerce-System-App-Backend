package com.example.commerce.dto;

import com.example.commerce.entity.CartItems;
import com.example.commerce.entity.User;
import com.example.commerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data

public class OrderDto {
    private Long id;
    private String orderDescription;
    private String address;
    private Float amount;
    private String payment;
    private OrderStatus orderStatus;
    private float totalAmount;
    private float discount;
    private UUID trackingId;
    private String userName;
    private Date date;
    private List<CartItems> cartItems;
}
