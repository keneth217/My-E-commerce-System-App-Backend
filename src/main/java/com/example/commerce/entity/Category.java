package com.example.commerce.entity;

import com.example.commerce.dto.CategoryDto;
import com.example.commerce.dto.ProductDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    public CategoryDto getCategoryDto(){
        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setDescription(description);
        categoryDto.setName(name);

        return categoryDto;

    }
}
