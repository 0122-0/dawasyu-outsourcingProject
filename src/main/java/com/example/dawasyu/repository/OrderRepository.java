package com.example.dawasyu.repository;

import com.example.dawasyu.domain.order.dto.response.OrderResponseDto;
import com.example.dawasyu.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    OrderResponseDto findOrderById (Long orderId);

}
