package com.example.commerce.dto;

import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Data
public class ProductDetailDto {
    private ProductDto productDto;
    private List<ReviewDto> reviewDtoList;
    private List<FaqDto> faqDtoList;
}
