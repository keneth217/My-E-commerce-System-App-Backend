package com.example.commerce.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReviewDto {
    private Long id;
    private Long rating;
    private String description;
    private MultipartFile image;
    private byte[] returnedImage;
    private Long productId;
    private Long userId;
    private String userName;
}
