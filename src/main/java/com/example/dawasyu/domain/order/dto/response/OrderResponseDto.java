package com.example.dawasyu.domain.order.dto.response;

import com.example.dawasyu.domain.order.entity.OrderStatus;
import com.example.dawasyu.domain.orderMenu.entity.OrderMenu;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponseDto {
    private final String orderNumber;

    private final List<OrderMenu> orderMenus;

    private final int totalPrice;

    private final LocalDateTime createdAt;

    private final OrderStatus orderStatus;

    public OrderResponseDto(String orderNumber, List<OrderMenu> orderMenus, int totalPrice, LocalDateTime createdAt, OrderStatus orderStatus) {
        this.orderNumber = orderNumber;
        this.orderMenus = orderMenus;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
    }
}
