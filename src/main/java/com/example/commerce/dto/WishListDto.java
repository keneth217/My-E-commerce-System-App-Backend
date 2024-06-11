package com.example.commerce.dto;

import lombok.Data;

@Data
public class WishListDto {
    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private String ProductDescription;
    private Float price;
    private  byte[] returnedImage;
}
