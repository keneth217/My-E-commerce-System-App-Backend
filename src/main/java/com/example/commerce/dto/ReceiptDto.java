package com.example.commerce.dto;

import lombok.Data;

@Data
public class ReceiptDto {
//    private Long id;
//    private Long userId;
//    private Long cartId;
//    private Float totalPrice;
//    private Long quantity;
//    private Float price;
private Long id;
    private String productName;
    private Float unitPrice;
    private Long quantity;
    private Float total;

}
