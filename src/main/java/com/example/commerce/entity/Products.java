package com.example.commerce.entity;

import com.example.commerce.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    @Lob
    private String description;
    private float price;
    private int discount;
    private float realAmount;
    private Long stock;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] image;
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="category_id",nullable = false)
    @OnDelete(action=OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    public ProductDto getProductDto(){
        ProductDto productDto=new ProductDto();
        productDto.setId(id);
        productDto.setDescription(description);
        productDto.setProductName(productName);
        productDto.setPrice(price);
        productDto.setStock(stock);
        productDto.setRealAmount(realAmount);
        productDto.setDiscount(discount);
        productDto.setReturnedImage(image);
        productDto.setCategoryId(category.getId());
        return productDto;

    }
    
}

