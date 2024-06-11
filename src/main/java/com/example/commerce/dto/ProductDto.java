package com.example.commerce.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {
    private Long id;
    private String productName;
    private String description;
    private float price;
    private int discount;
    private float realAmount;
    private MultipartFile image;
    private byte[] returnedImage;
    private Long categoryId;
    private String CategoryName;
    private  Long quantity;
    private Long stock;
}
