package com.example.dawasyu.repository;

import com.example.dawasyu.common.error.CustomException;
import com.example.dawasyu.common.error.ErrorCode;
import com.example.dawasyu.domain.order.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findOrderById (Long orderId);

    default Order findOrderByIdOrElseThrow(Long orderId){
        return findById(orderId).orElseThrow(()->new CustomException(ErrorCode.ORDER_NOT_FOUND));
    }
}
