package com.example.commerce.services.Admin.Category;

import com.example.commerce.dto.CategoryDto;
import com.example.commerce.dto.ProductDto;
import com.example.commerce.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    List<Category> getAllCategories();

    CategoryDto getCategoryById(Long id);
    public long getCategoryCount();
}
