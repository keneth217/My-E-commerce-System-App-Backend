package com.example.commerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class SignUpRequest {
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5,max = 30,message = "Customer name should be btw 5 and 30 characters")
    private String firstName;
    @NotEmpty(message = "Name cannot be null or empty")
    private String lastName;
    @NotEmpty(message = "phone cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]){10}",message = "mobile must be 10 digits")
    private String phone;
    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message="Enter valid email address")
    private String email;
    @NotEmpty(message = "password cannot be null or empty")
    private String password;
}
