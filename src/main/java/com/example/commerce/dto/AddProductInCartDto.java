package com.example.commerce.dto;

import com.example.commerce.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class AddProductInCartDto {
    private Long userId;
    private Long productId;

}
