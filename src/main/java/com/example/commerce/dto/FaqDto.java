package com.example.commerce.dto;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class FaqDto {
    private Long id;
    private String answer;

    private String question;
    private Long productId;
}
