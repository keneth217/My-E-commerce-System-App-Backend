package com.example.commerce.controller.admin;

import com.example.commerce.dto.CategoryDto;
import com.example.commerce.dto.ProductDto;
import com.example.commerce.entity.Category;
import com.example.commerce.services.Admin.Category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
//@RequestMapping(path="/api/admin",produces = {MediaType.APPLICATION_JSON_VALUE})
@RequestMapping(path="/api/admin")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @GetMapping("/cat/count")
    public long getProductCount() {
        return categoryService.getCategoryCount();
    }

        @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        return  new ResponseEntity<>(categoryService.createCategory(categoryDto),HttpStatus.CREATED);


    }
    @GetMapping("/categories")
    public  ResponseEntity<List<Category>> getAll(){
        List<Category> category=categoryService.getAllCategories();
        return ResponseEntity.ok(category);

    }
    @GetMapping("category/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id){
        CategoryDto categoryDto=categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryDto);
    }
}
