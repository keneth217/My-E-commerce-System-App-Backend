package com.example.commerce.services.Customer.wishList;

import com.example.commerce.dto.WishListDto;

import java.util.List;

public interface WishListService {
    WishListDto addProductToWishList(WishListDto wishListDto);
    List<WishListDto> getAllWishlistBYUserId(Long userId);
}
