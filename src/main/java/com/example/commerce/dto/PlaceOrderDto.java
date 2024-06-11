package com.example.commerce.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PlaceOrderDto {
    private Long userId;
    private String address;
    private String orderDescription;
    private Date date;

}
