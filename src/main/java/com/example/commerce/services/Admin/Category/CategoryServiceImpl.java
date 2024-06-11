package com.example.commerce.services.Admin.Category;

import com.example.commerce.dto.CategoryDto;
import com.example.commerce.entity.Category;

import com.example.commerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryDto createCategory(CategoryDto categoryDto){
        Category category=new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return categoryRepository.save(category).getCategoryDto();
    }
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Optional<Category> optionalCategory=categoryRepository.findById(id);

        return optionalCategory.map(Category::getCategoryDto).orElseThrow(()-> new RuntimeException("category with id:"+id+ "not found"));
    }
    public long getCategoryCount() {
        return categoryRepository.count();
    }
}
