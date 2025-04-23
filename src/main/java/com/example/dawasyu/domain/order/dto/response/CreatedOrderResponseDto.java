package com.example.dawasyu.domain.order.dto.response;

import com.example.dawasyu.domain.orderMenu.entity.OrderMenu;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreatedOrderResponseDto {


    private final List<OrderMenu> orderMenus;

    private final int totalPrice;

    private final LocalDateTime createdAt;

    public CreatedOrderResponseDto(List<OrderMenu> orderMenus, int totalPrice, LocalDateTime createdAt) {

        this.orderMenus = orderMenus;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }
}
