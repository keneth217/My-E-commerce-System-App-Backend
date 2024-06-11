package com.example.commerce.services.Customer.cart;

import com.example.commerce.dto.*;
import com.example.commerce.entity.*;
import com.example.commerce.enums.OrderStatus;
import com.example.commerce.enums.PaymentMethod;
import com.example.commerce.exceptions.CustomerAlreadyExistException;
import com.example.commerce.exceptions.ProductNotFoundException;
import com.example.commerce.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServicesImpl implements  CartServices{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemsRepository cartItemsRepository ;

    private static final Logger logger = LoggerFactory.getLogger(CartServices.class);

    @Transactional
    public ResponseEntity<?> addProductsToCart(AddProductInCartDto addProductInCartDto) {
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductsIdAndUserId(
                addProductInCartDto.getProductId(), addProductInCartDto.getUserId());

        if (optionalCartItems.isPresent()) {
            logger.info("Product already in cart for user id: {}", addProductInCartDto.getUserId());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already in cart");
        } else {
            Optional<Products> optionalProducts = productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

            if (optionalProducts.isPresent() && optionalUser.isPresent()) {
                Products product = optionalProducts.get();
                User user = optionalUser.get();

                // Check if the stock is sufficient
                if (product.getStock() <= 0) {
                    logger.warn("Insufficient stock for product id: {}", addProductInCartDto.getProductId());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient stock");
                }

                // Reduce stock by one when adding to cart
                product.setStock(product.getStock() - 1);
                productRepository.save(product);

                // Add product to cart
                CartItems cartItem = new CartItems();
                cartItem.setProducts(product);
                cartItem.setUnitPrice(product.getRealAmount());
                cartItem.setPrice(product.getRealAmount());
                cartItem.setQuantity(1L);
                cartItem.setUser(user);
                CartItems updatedCartItem = cartItemsRepository.save(cartItem);

                // Calculate the total amount in cart
                List<CartItems> cartItemsList = cartItemsRepository.findByUserId(addProductInCartDto.getUserId());
                double totalAmount = cartItemsList.stream()
                        .mapToDouble(CartItems::getPrice)
                        .sum();


                logger.info("Added product to cart and updated receipt: {}", updatedCartItem);
                return ResponseEntity.status(HttpStatus.CREATED).body(updatedCartItem.getId());
            } else {
                logger.error("User or product not found. User ID: {}, Product ID: {}",
                        addProductInCartDto.getUserId(), addProductInCartDto.getProductId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
            }
        }
    }

    public OrderDto getOrderByUserId(Long userId){
        Orders activeOrders=orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.pending);
        List<CartItems> cartItems=cartItemsRepository.findByUserId(userId);
//        List<CartItemDto> cartItemDtoList = activeOrders.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());
        OrderDto orderDto=new OrderDto();
        orderDto.setAmount(activeOrders.getAmount());
        orderDto.setId(activeOrders.getId());
        orderDto.setOrderStatus(activeOrders.getOrderStatus());
        orderDto.setTotalAmount(activeOrders.getTotalAmount());
        orderDto.setCartItems(cartItems);
        return orderDto;
    }
    @Transactional
    public CartItemDto increaseQuantity(Long cartItemId) {

        // Fetch the cart item for the given cart item ID
        CartItems cartItems = cartItemsRepository.findById(cartItemId)
                .orElseThrow(() -> new ProductNotFoundException("Cart item not found for the given cart item ID"));

        // Fetch the product associated with the cart item
        Products product = cartItems.getProducts();

        // Check if there is enough stock to increase the quantity
        if (product.getStock() < 1) {
            throw new ProductNotFoundException("Insufficient stock to increase quantity");
        }

        // Increase the quantity of the cart item
        cartItems.setQuantity(cartItems.getQuantity() + 1);
        cartItems.setPrice(cartItems.getPrice() + cartItems.getUnitPrice()); // assuming unit price is fixed

        // Reduce the stock of the product by one and save
        product.setStock(product.getStock() - 1);
        productRepository.save(product);

        // Save the updated cart item
        cartItemsRepository.save(cartItems);

        // Return the updated cart item DTO
        return cartItems.getCartDto();
    }


    @Transactional
    public CartItemDto decreaseQuantity(Long cartItemId) {

        // Fetch the cart item for the given cart item ID
        CartItems cartItems = cartItemsRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found for the given cart item ID"));

        cartItems.setQuantity(cartItems.getQuantity() - 1);
        cartItems.setPrice(cartItems.getPrice() - cartItems.getUnitPrice()); // assuming unit price is fixed

        // Save the updated cart item
        cartItemsRepository.save(cartItems);

        // Return the updated cart item DTO
        return cartItems.getCartDto();
    }
    @Transactional
    public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
        try {
            // Fetch the user by ID
            User user = userRepository.findById(placeOrderDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Fetch the cart items for the user
            List<CartItems> cartItemsList = cartItemsRepository.findByUserId(placeOrderDto.getUserId());

            if (cartItemsList.isEmpty()) {
                throw new RuntimeException("Cart is empty");
            }

            // Generate a unique tracking ID
            UUID trackingId = generateUniqueTrackingId();

            // Calculate the total amount of cart items
            double totalAmount = cartItemsList.stream()
                    .mapToDouble(CartItems::getPrice)
                    .sum();

            // Create a new order
            Orders newOrder = new Orders();
            newOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            newOrder.setAddress(placeOrderDto.getAddress());
            newOrder.setPayment(String.valueOf(PaymentMethod.m_pesa));
            // Format the date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date formattedDate = sdf.parse(sdf.format(new Date()));
            newOrder.setDate(formattedDate);

            newOrder.setTotalAmount((float) totalAmount);
            newOrder.setOrderStatus(OrderStatus.placed);
            newOrder.setTrackingId(trackingId);
            newOrder.setUser(user);

            // Save the new order
            Orders savedOrder = orderRepository.save(newOrder);

            // Delete cart items after placing the order
            cartItemsRepository.deleteAll(cartItemsList);

            // Return the DTO of the saved order
            return savedOrder.getOrderDto();
        } catch (Exception e) {
            // Log the error (consider using a logging framework)
            System.err.println("Error placing order: " + e.getMessage());
            e.printStackTrace();

            // Throw a custom exception or handle the error accordingly
            throw new RuntimeException("Error placing order", e);
        }
    }


    private UUID generateUniqueTrackingId() {
        UUID trackingId;
        boolean exists;
        do {
            trackingId = UUID.randomUUID();
            exists = orderRepository.existsByTrackingId(trackingId);
        } while (exists);
        return trackingId;
    }
    public void deleteCartItem(Long id) {
        Optional<CartItems> cartItem = cartItemsRepository.findById(id);
        if (cartItem.isPresent()) {
            cartItemsRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }
    public List<OrderDto> getMyPlacedOrders(Long userId){
        return orderRepository.findByUserIdAndOrderStatusIn(userId,List.of(OrderStatus.placed,OrderStatus.shipped,OrderStatus.delivered)).stream()
                .map(Orders::getOrderDto).collect(Collectors.toList());
    }

    public OrderDto searchOrderByTrackingId(UUID trackingId){
        Optional<Orders> optionalOrders=orderRepository.findByTrackingId(trackingId);
        if (optionalOrders.isPresent()){
            return optionalOrders.get().getOrderDto();
        }
        return  null;
    }

    public List<CartItemDto> getCartItems(Long userId){
        List<CartItems> optionalCartItems=cartItemsRepository.findByUserId(userId);
        if(optionalCartItems.isEmpty()){
            throw new RuntimeException("no items in cart");
        }else{
            return cartItemsRepository.findByUserId(userId).stream().map(CartItems::getCartDto).collect(Collectors.toList());
        }
    }



}
