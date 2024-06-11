package com.example.commerce.dto;

import com.example.commerce.entity.User;
import com.example.commerce.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class AuthenthicationResponse {
    private ResponseDto responseDto;
    private String token;
    private User user;

}
