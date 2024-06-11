package com.example.commerce.controller.auth;

import com.example.commerce.constants.AuthConstants;
import com.example.commerce.dto.AuthenthicationRequest;
import com.example.commerce.dto.AuthenthicationResponse;
import com.example.commerce.dto.ResponseDto;
import com.example.commerce.dto.SignUpRequest;
import com.example.commerce.services.Auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final AuthService authService;
    @PostMapping("/sign")
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody SignUpRequest signUpRequest){
    authService.createCustomer(signUpRequest);
        String userName= signUpRequest.getFirstName()+" "+signUpRequest.getLastName();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AuthConstants.ACCOUNT_CREATION_CODE,userName+" "+AuthConstants.ACCOUNT_CREATION));
//        return  new ResponseEntity<>("HELLO"+" "+userName+" "+"ACCOUNT CREATION SUCCESS,WELCOME TO OUR E-COMMERCE SERVICE",HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenthicationResponse> login(@RequestBody AuthenthicationRequest authenticationRequest){
        return ResponseEntity.ok(authService.createAuthToken(authenticationRequest));
    }
}
