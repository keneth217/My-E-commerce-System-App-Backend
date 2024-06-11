package com.example.commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data

public class DiscountDto {
    private Long id;
    private String discountName;
    private float discount;
    private Date expirationDate;
}
