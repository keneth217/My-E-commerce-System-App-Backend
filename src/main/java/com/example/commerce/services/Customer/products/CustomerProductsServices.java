package com.example.commerce.services.Customer.products;

import com.example.commerce.dto.ProductDetailDto;
import com.example.commerce.dto.ProductDto;
import com.example.commerce.dto.ProductResponseDto;

import java.util.List;

public interface CustomerProductsServices {
    List<ProductDto> getAllProducts();
    List<ProductDto> searchProduct(String query);
    List<ProductDto> getAllProductsByName(String name);
    ProductResponseDto getAllPageableProducts(int pageNumber);
    ProductDetailDto getProductDetailById(Long productId);
}
