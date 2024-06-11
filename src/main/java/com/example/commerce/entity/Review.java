package com.example.commerce.entity;

import com.example.commerce.dto.ReviewDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long rating;
    @Lob
    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] image;
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="user_id",nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="product_id",nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JsonIgnore
    private Products products ;

    public ReviewDto getReviewDto(){
        ReviewDto reviewDto=new ReviewDto();
        reviewDto.setId(id);
        reviewDto.setDescription(description);
        reviewDto.setRating(rating);
        reviewDto.setProductId(products.getId());
        reviewDto.setUserId(user.getId());
        reviewDto.setReturnedImage(image);
        reviewDto.setUserName(user.getFirstName());
        return reviewDto;

    };
}
