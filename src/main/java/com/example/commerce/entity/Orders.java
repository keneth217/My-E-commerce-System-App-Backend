package com.example.commerce.entity;

import com.example.commerce.dto.OrderDto;
import com.example.commerce.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderDescription;
    private String address;
    private Float amount;
    private String payment;
    private OrderStatus orderStatus;
    private float totalAmount;
    private float discount;
    @Column(unique = true)
    private UUID trackingId;
    @Temporal(TemporalType.DATE)
    @Column(name = "order_date")
    private Date date;
//    @OneToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name="user_id",referencedColumnName = "id")
//    private User user;
@ManyToOne(fetch = FetchType.LAZY, optional=false)
@JoinColumn(name="user_id",nullable = false)
@OnDelete(action= OnDeleteAction.CASCADE)
@JsonIgnore
private User user;

    public OrderDto getOrderDto() {
        OrderDto orderDto=new OrderDto();
        orderDto.setId(id);
        orderDto.setAmount(amount);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setOrderDescription(orderDescription);
        orderDto.setDiscount(discount);
        orderDto.setTotalAmount(totalAmount);
        orderDto.setTrackingId(trackingId);
        orderDto.setUserName(user.getFirstName());
        orderDto.setPayment(payment);
        orderDto.setDate(date);
        orderDto.setAddress(address);
//        orderDto.setCartItems(List<cartItems>);

        return  orderDto;
    }
}
