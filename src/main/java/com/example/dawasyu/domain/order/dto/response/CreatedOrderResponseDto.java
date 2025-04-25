package com.example.dawasyu.domain.order.dto.response;

import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.orderMenu.dto.response.OrderMenuResponseDto;
import com.example.dawasyu.domain.orderMenu.entity.OrderMenu;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreatedOrderResponseDto {
    private final String orderNumber;
    private final List<OrderMenuResponseDto> orderMenus;
    private final Long totalPrice;
    private final LocalDateTime createdAt;

    public CreatedOrderResponseDto(Order order) {
        this.orderNumber = order.getOrderNumber();
        this.totalPrice = order.getTotalPrice();
        this.createdAt = order.getCreatedAt();
        this.orderMenus = order.getOrderMenus().stream()
                .map(OrderMenuResponseDto::new)
                .collect(Collectors.toList());
    }
}