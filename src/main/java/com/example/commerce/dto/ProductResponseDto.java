package com.example.commerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponseDto {
    private List<ProductDto> productDtoList;
    private Integer totalPages;
    private Integer pageNumber;
}
