package com.example.commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthenthicationRequest {
    private String phone;
    private String password;
}
