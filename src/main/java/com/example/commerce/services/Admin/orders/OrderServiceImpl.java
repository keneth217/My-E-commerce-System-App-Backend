package com.example.commerce.services.Admin.orders;

import com.example.commerce.dto.AnalyticResponseDto;
import com.example.commerce.dto.OrderDto;
import com.example.commerce.dto.OrdersEarningsDto;
import com.example.commerce.entity.Orders;
import com.example.commerce.enums.OrderStatus;
import com.example.commerce.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements  OrderService{
    private final OrderRepository orderRepository;

    public long getOrderCount() {
        return orderRepository.count();
    }
    public List<OrderDto> getAllOrders(){
        List<Orders> ordersList=orderRepository.findAllByOrderStatusIn(List.of(OrderStatus.placed,OrderStatus.shipped,OrderStatus.delivered));
        return  ordersList.stream().map(Orders::getOrderDto).collect(Collectors.toList());
    }
    public  OrderDto changeOrderStatus(Long orderId,String Status){
        Optional<Orders> optionalOrders=orderRepository.findById(orderId);
        if (optionalOrders.isPresent()){
            Orders orders=optionalOrders.get();

            if (Objects.equals(Status,"shipped")){
                orders.setOrderStatus(OrderStatus.shipped);

            } else if (Objects.equals(Status,"delivered")) {
                orders.setOrderStatus(OrderStatus.delivered);
            }
            return orderRepository.save(orders).getOrderDto();
        }
return null;
    }

    @Transactional
    public AnalyticResponseDto adminAnalytics() {
        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonthDate = currentDate.minusMonths(1);

        Float currentMonthOrders = getTotalOrdersForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Float previousMonthOrders = getTotalOrdersForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());

        Float currentMonthEarnings = getTotalEarningsForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Float previousMonthEarnings = getTotalEarningsForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());

        Long placed = orderRepository.countByOrderStatus(OrderStatus.placed);
        Long shipped = orderRepository.countByOrderStatus(OrderStatus.shipped);
        Long delivered = orderRepository.countByOrderStatus(OrderStatus.delivered);

        System.out.println("------current------month------transactions");
        System.out.println(currentMonthOrders);
        System.out.println(currentMonthEarnings);

        System.out.println("------previous------month------transactions");
        System.out.println(previousMonthOrders);
        System.out.println(previousMonthEarnings);

        return new AnalyticResponseDto(
                placed,
                shipped,
                delivered,
                previousMonthOrders,
                currentMonthOrders,
                previousMonthEarnings,
                currentMonthEarnings);
    }
    private Float getTotalOrdersForMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startOfMonth = calendar.getTime();

        // Move calendar to end of specified month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endOfMonth = calendar.getTime();

        List<Orders> ordersList = orderRepository.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.delivered);
        return (float) ordersList.size();
    }
    private Float getTotalEarningsForMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startOfMonth = calendar.getTime();

        // Move calendar to end of specified month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endOfMonth = calendar.getTime();

        List<Orders> ordersList = orderRepository.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.delivered);
        Float sum = 0F;
        for (Orders order : ordersList) {
            sum += order.getTotalAmount();
        }
        return sum;
    }
    
    
     public OrdersEarningsDto getOrdersAndEarningsBetweenDates(Date startDate, Date endDate) {
        List<Orders> ordersList = orderRepository.findByDateBetweenAndOrderStatus(startDate, endDate, OrderStatus.delivered);
long totalOrders=ordersList.stream().count();
        List<OrderDto> orders = ordersList.stream()
                .map(Orders::getOrderDto)
                .collect(Collectors.toList());
         System.out.println("total delivered orders:"+totalOrders);
         System.out.println("total delivered order list delivered:"+orders);


        Float totalEarnings = (float) ordersList.stream()
                .mapToDouble(Orders::getTotalAmount)
                .sum();
         System.out.println("total delivered total earnings:"+totalEarnings);

        return new OrdersEarningsDto(totalOrders, totalEarnings,orders);
    }


}
