package com.example.commerce.controller.customer;

import com.example.commerce.constants.AuthConstants;
import com.example.commerce.constants.CartsConstants;
import com.example.commerce.dto.*;
import com.example.commerce.services.Customer.cart.CartServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {
    private final CartServices cartServices;
    @PostMapping("/cart")
    public ResponseEntity<ResponseDto> addCart(@RequestBody AddProductInCartDto addProductInCartDto){
       cartServices.addProductsToCart(addProductInCartDto);
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(new ResponseDto(CartsConstants.ADD_CART_CODE,CartsConstants.ADD_CART));
    }
    @GetMapping("/cart/{userId}")
    public ResponseEntity<?> getOrderByUserId(@PathVariable Long userId){
        OrderDto orderDto=cartServices.getOrderByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }
    @DeleteMapping("/item/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long id) {
        try {
            cartServices.deleteCartItem(id);
            return new ResponseEntity<>("Cart item deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
@PutMapping("/increase/{cartItemId}")
public ResponseEntity<CartItemDto> increaseQuantity(@PathVariable Long cartItemId) {
    try {
        CartItemDto updatedCartItem = cartServices.increaseQuantity(cartItemId);
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
    @PutMapping("/decrease/{cartItemId}")
    public ResponseEntity<CartItemDto> decreaseQuantity(@PathVariable Long cartItemId) {
        try {
            CartItemDto updatedCartItem = cartServices.decreaseQuantity(cartItemId);
            return new ResponseEntity<>(updatedCartItem,HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR); // Internal Server Error
        }
    }
@PostMapping("/placeOrder")
public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
    OrderDto orderDto = cartServices.placeOrder(placeOrderDto);
    return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
}
    @GetMapping("/myorders/{userId}")
    public ResponseEntity<?> myOrders(@PathVariable Long userId){
        return ResponseEntity.ok(cartServices.getMyPlacedOrders(userId)) ;
    }

    @GetMapping("/items/{userId}")
    public ResponseEntity<?> myCart(@PathVariable Long userId){
        return ResponseEntity.ok(cartServices.getCartItems(userId)) ;
    }

}
