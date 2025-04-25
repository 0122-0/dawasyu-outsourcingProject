package com.example.dawasyu.domain.order.dto.response;

import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.order.entity.OrderStatus;
import com.example.dawasyu.domain.orderMenu.dto.response.OrderMenuResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponseDto {
    private final String orderNumber;

    private final List<OrderMenuResponseDto> orderMenus;

    private final LocalDateTime createdAt;

    private final OrderStatus orderStatus;

    public OrderResponseDto(Order order) {
        this.orderNumber = order.getOrderNumber();
        this.orderMenus = order.getOrderMenus().stream()
                .map(OrderMenuResponseDto::new)
                .collect(Collectors.toList());
        this.createdAt = order.getCreatedAt();
        this.orderStatus = order.getOrderStatus();

    }

}
