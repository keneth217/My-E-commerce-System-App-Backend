package com.example.commerce.controller.customer;

import com.example.commerce.dto.WishListDto;
import com.example.commerce.services.Customer.wishList.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;
    @PostMapping("/wishlist")
    public ResponseEntity<WishListDto> postWislist(@RequestBody WishListDto wishListDto){
        WishListDto wishListDto1=wishListService.addProductToWishList(wishListDto);
        if (wishListDto1 == null) {
            return ResponseEntity.notFound().build();
        }
        else{
            return  ResponseEntity.ok(wishListDto1);
        }
    }
    @GetMapping("/wishlist/{userId}")
    public ResponseEntity<List<WishListDto>> geAll(@PathVariable Long userId){
        List<WishListDto> wishListDtoList=wishListService.getAllWishlistBYUserId(userId);
        if (wishListDtoList == null) {
            return ResponseEntity.notFound().build();
        }
        else{
            return  ResponseEntity.ok(wishListDtoList);
        }
    }
}
