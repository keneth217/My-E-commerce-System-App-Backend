package com.example.commerce.entity;

import com.example.commerce.dto.CartItemDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "cart_items")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float price;
    private Float unitPrice;
    private Long quantity;
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="product_id",nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JsonIgnore
    private Products products;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="user_id",nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY, optional=false)
//    @JoinColumn(name="order_id",nullable = false)
//    @OnDelete(action= OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private Orders orders;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="order_id")
//    @JsonIgnore
//    private  Orders orders;

    public CartItemDto getCartDto(){
        CartItemDto cartItemDto=new CartItemDto();
        cartItemDto.setId(id);
        cartItemDto.setPrice(price);
        cartItemDto.setQuantity(quantity);
        cartItemDto.setProductName(products.getProductName());
        cartItemDto.setUserId(user.getId());
        cartItemDto.setProductId(products.getId());
        cartItemDto.setReturnedImage(getProducts().getImage());
        cartItemDto.setTotalAmount(cartItemDto.getTotalAmount());
        return  cartItemDto;
    }
}
