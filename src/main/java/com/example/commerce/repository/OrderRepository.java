package com.example.commerce.repository;

import com.example.commerce.entity.Orders;
import com.example.commerce.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {

    List<Orders> findByUserIdAndOrderStatusIn(Long userId, List<OrderStatus> orderStatus);
    List<Orders> findAllByOrderStatusIn(List<OrderStatus> orderStatusList);

    Orders findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    Optional<Orders> findByTrackingId(UUID trackingId);

    List<Orders> findByDateBetweenAndOrderStatus(Date startOfMonth, Date endOfMonth, OrderStatus orderStatus);

    Long countByOrderStatus(OrderStatus orderStatus);

    boolean existsByTrackingId(UUID trackingId);


    Optional<Orders> findByUserId(Long userId);
}
