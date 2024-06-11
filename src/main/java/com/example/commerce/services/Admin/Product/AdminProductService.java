package com.example.commerce.services.Admin.Product;

import com.example.commerce.dto.ProductDto;
import com.example.commerce.dto.ProductResponseDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {
    ProductDto addProduct (ProductDto productDto) throws IOException;
    List<ProductDto> getAllProducts();
    List<ProductDto> searchProduct(String query);

    ProductDto  getProductById(Long id);


    ProductDto updateProduct( Long id,ProductDto productDto) throws IOException;

    void deleteProduct(Long id);
//    ProductDto getProductBYId(Long productId);
    ProductResponseDto getAllPageableProducts(int pageNumber);
    public long getProductCount();

}
