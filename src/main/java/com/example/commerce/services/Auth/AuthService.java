package com.example.commerce.services.Auth;

import com.example.commerce.dto.AuthenthicationRequest;
import com.example.commerce.dto.AuthenthicationResponse;
import com.example.commerce.dto.SignUpRequest;
import com.example.commerce.entity.User;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    User createCustomer(SignUpRequest signUpRequest);
    Boolean hasCustomerWithPhone(String phone);
    AuthenthicationResponse createAuthToken(@RequestBody AuthenthicationRequest authenticationRequest);
}
