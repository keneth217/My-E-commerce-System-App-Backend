package com.example.commerce.services.Customer.cart;

import com.example.commerce.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CartServices {
    ResponseEntity<?> addProductsToCart(AddProductInCartDto addProductInCartDto);
    OrderDto getOrderByUserId(Long userId);
//    CartItemDto increaseQuantity(AddProductInCartDto addProductInCartDto);
    CartItemDto increaseQuantity(Long cartItemId);
    CartItemDto decreaseQuantity(Long cartItemId);
    OrderDto placeOrder(PlaceOrderDto placeOrderDto);
    List<OrderDto> getMyPlacedOrders(Long userId);
    OrderDto searchOrderByTrackingId(UUID trackingId);
    void deleteCartItem(Long id);
    List<CartItemDto> getCartItems(Long userId);

}
