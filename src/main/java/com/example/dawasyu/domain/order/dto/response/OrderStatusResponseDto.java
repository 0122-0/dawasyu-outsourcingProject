package com.example.dawasyu.domain.order.dto.response;

import com.example.dawasyu.domain.order.entity.OrderStatus;
import lombok.Getter;

@Getter
public class OrderStatusResponseDto {

    private final OrderStatus orderStatus;

    public OrderStatusResponseDto(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
