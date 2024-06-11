package com.example.commerce.repository;

import com.example.commerce.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CartItemsRepository extends JpaRepository<CartItems,Long> {
//    Optional<CartItems> findByProductsIdAndOrdersIdAndUserId(Long productId ,Long orderId,Long userId);

    Optional<CartItems> findByProductsIdAndUserId(Long productId, Long userId);

//    Optional<CartItems> findByUserIdAndProductId(Long userId, Long productId);

    List<CartItems> findByUserId(Long userId);

//    Optional<CartItems> findByUserIdAndProductId(Long userId, Long productId);
}
