package com.example.dawasyu.domain.order.dto.request;

import com.example.dawasyu.domain.order.entity.OrderStatus;
import lombok.Getter;

@Getter
public class OrderStatusRequestDto {

    private final OrderStatus oldOrderStatus;

    private final OrderStatus newOrderStatus;

    public OrderStatusRequestDto(OrderStatus oldOrderStatus, OrderStatus newOrderStatus) {
        this.oldOrderStatus = oldOrderStatus;
        this.newOrderStatus = newOrderStatus;
    }
}
