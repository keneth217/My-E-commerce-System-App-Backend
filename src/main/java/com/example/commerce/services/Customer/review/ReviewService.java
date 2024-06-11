package com.example.commerce.services.Customer.review;

import com.example.commerce.dto.OrderedProductsResponseDto;
import com.example.commerce.dto.ReviewDto;

import java.io.IOException;

public interface ReviewService {
    OrderedProductsResponseDto getOrderedProductsByOrderId(Long orderId);
    ReviewDto giveReview(ReviewDto reviewDto) throws IOException;
}
