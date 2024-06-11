package com.example.commerce.controller.customer;


import com.example.commerce.dto.*;
import com.example.commerce.entity.Category;
import com.example.commerce.services.Admin.Category.CategoryService;
import com.example.commerce.services.Customer.products.CustomerProductsServices;
import com.example.commerce.services.Customer.review.ReviewService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerProductController {
    private final CustomerProductsServices customerServices;
    private final ReviewService reviewService;
    private final CategoryService categoryService;
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
List<ProductDto> productDtoList=customerServices.getAllProducts();
        return  ResponseEntity.ok(productDtoList);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProduct(@RequestParam("query") String query) {
        List<ProductDto> productDtoList = customerServices.searchProduct(query);
        return ResponseEntity.ok(productDtoList);
    }
    @GetMapping("/all/products/{pageNumber}")
    public ResponseEntity<?> getAllPageableProducts(@PathVariable int pageNumber){
        return  ResponseEntity.ok(customerServices.getAllPageableProducts(pageNumber));
    }
    @GetMapping("/ordered-products/{orderId}")
    public ResponseEntity<OrderedProductsResponseDto> orderedProductsDetailsById(@PathVariable Long orderId){
        return  ResponseEntity.ok(reviewService.getOrderedProductsByOrderId(orderId));
    }
    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@ModelAttribute ReviewDto reviewDto) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.giveReview(reviewDto));
    }
    @GetMapping("/products-details/{productId}")
    public
     ResponseEntity<ProductDetailDto> getProductDetailById(@PathVariable Long productId){
        ProductDetailDto productDetailDto=customerServices.getProductDetailById(productId);
        if (productDetailDto == null) {
           return ResponseEntity.notFound().build();
        }
        else{
            return  ResponseEntity.ok(productDetailDto);
        }
    }
    @GetMapping("/products/categories")
    public  ResponseEntity<List<Category>> getAll(){
        List<Category> category=categoryService.getAllCategories();
        return ResponseEntity.ok(category);

    }
    @GetMapping("/products/category/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id){
        CategoryDto categoryDto=categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryDto);
    }

}
