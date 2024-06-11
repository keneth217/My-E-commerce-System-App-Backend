package com.example.commerce.controller.all;

import com.example.commerce.dto.ProductDto;
import com.example.commerce.services.Customer.products.CustomerProductsServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;




@RestController
@RequestMapping("/api/all")
@RequiredArgsConstructor

public class AllPublicControllers {
    private final CustomerProductsServices customerServices;
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDtoList=customerServices.getAllProducts();
        return  ResponseEntity.ok(productDtoList);
    }
}
