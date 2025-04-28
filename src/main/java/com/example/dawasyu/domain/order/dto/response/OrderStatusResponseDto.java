package com.example.dawasyu.domain.order.dto.response;


import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.order.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderStatusResponseDto {

    private final OrderStatus orderStatus;

    private final LocalDateTime updatedAt;

    private final Long orderId;

    private final Long storeId;

    public OrderStatusResponseDto(Order order) {
        this.orderStatus = order.getOrderStatus();
        this.updatedAt = order.getUpdatedAt();
        this.orderId = order.getId();
        this.storeId = order.getOrderMenus().stream()
                .map(orderMenu -> orderMenu.getMenu().getStore().getId())
                .findFirst()
                .orElseThrow(()-> new RuntimeException("가게를 찾을수 없습니다."));
    }
}