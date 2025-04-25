package com.example.dawasyu.domain.orderMenu.dto.response;

import com.example.dawasyu.domain.orderMenu.entity.OrderMenu;
import lombok.Getter;

@Getter
public class OrderMenuResponseDto {
    private final Long id;
    private final String menuName;
    private final int quantity;

    public OrderMenuResponseDto(OrderMenu orderMenu) {
        this.id = orderMenu.getId();
        this.menuName = orderMenu.getMenu().getName();
        this.quantity = orderMenu.getQuantity();
    }
}