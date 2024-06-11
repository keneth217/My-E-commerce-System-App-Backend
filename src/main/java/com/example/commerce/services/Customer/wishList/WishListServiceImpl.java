package com.example.commerce.services.Customer.wishList;

import com.example.commerce.dto.WishListDto;
import com.example.commerce.entity.Products;
import com.example.commerce.entity.User;
import com.example.commerce.entity.WishList;
import com.example.commerce.repository.ProductRepository;
import com.example.commerce.repository.UserRepository;
import com.example.commerce.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService{
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WishListRepository wishListRepository;


    public WishListDto addProductToWishList(WishListDto wishListDto){
        Optional<Products> optionalProducts=productRepository.findById(wishListDto.getProductId());
        Optional<User> optionalUser=userRepository.findById(wishListDto.getUserId());
        if (optionalUser.isPresent() && optionalProducts.isPresent()) {
            WishList wishList=new WishList();
            wishList.setProducts(optionalProducts.get());
            wishList.setUser(optionalUser.get());
            return  wishListRepository.save(wishList).getWishListDto();
        }
        return  null;
    }
    public List<WishListDto> getAllWishlistBYUserId(Long userId){
        return wishListRepository.findAllByUserId(userId).stream().map(WishList::getWishListDto).collect(Collectors.toList());
    }

}
