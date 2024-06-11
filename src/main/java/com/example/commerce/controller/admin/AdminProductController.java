package com.example.commerce.controller.admin;

import com.example.commerce.constants.ProductsConstants;
import com.example.commerce.dto.FaqDto;
import com.example.commerce.dto.ProductDto;
import com.example.commerce.services.Admin.Product.AdminProductService;
import com.example.commerce.services.Admin.faq.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {
    private final AdminProductService adminService;
private final FaqService faqService;

    @GetMapping("/count")
    public long getProductCount() {
        return adminService.getProductCount();
    }
    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@ModelAttribute ProductDto productDto) throws IOException {
        return new ResponseEntity<>(adminService.addProduct(productDto), HttpStatus.CREATED);
    }
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return  ResponseEntity.ok(adminService.getAllProducts());
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProduct(@RequestParam("query") String query) {
        List<ProductDto> productDtoList = adminService.searchProduct(query);
        return ResponseEntity.ok(productDtoList);
    }
    @PutMapping("product/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto productDto1=adminService.updateProduct(id,productDto);
        return ResponseEntity.ok(productDto1);
    }
    @GetMapping("product/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        ProductDto productDto=adminService.getProductById(id);
        return ResponseEntity.ok(productDto);
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        adminService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/faq/{productId}")
    public ResponseEntity<FaqDto> postFAq(@PathVariable Long productId, @RequestBody FaqDto faqDto){

        return  ResponseEntity.status(HttpStatus.CREATED).body(faqService.postFAq(productId, faqDto));
    }

    @GetMapping("/all/products/{pageNumber}")
    public ResponseEntity<?> getAllPageableProducts(@PathVariable int pageNumber){
        return  ResponseEntity.ok(adminService.getAllPageableProducts(pageNumber));
    }
}
