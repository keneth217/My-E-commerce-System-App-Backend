package com.example.commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
public class CartItemDto {
    private Long id;
    private Float price;
    private Long quantity;
    private Long productId;
    private Long orderId;
    private String productName;
    private Float unitPrice;
    private Float totalInCArt;
    private Long userId;
    private byte[] returnedImage;
    private Float totalAmount;
}
