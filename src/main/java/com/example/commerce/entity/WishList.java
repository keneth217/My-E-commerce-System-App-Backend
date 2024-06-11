package com.example.commerce.entity;

import com.example.commerce.dto.WishListDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
@Data
@Entity
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    public WishListDto getWishListDto() {
        WishListDto wishListDto= new WishListDto();
        wishListDto.setId(id);
        wishListDto.setProductId(products.getId());
        wishListDto.setProductName(products.getProductName());
        wishListDto.setProductDescription(products.getDescription());
        wishListDto.setReturnedImage(products.getImage());
        wishListDto.setUserId(user.getId());
        return  wishListDto;
    }
}
