package com.example.dawasyu.domain.order.dto.request;

import com.example.dawasyu.domain.orderMenu.entity.OrderMenu;
import lombok.Getter;

import java.util.List;

@Getter
public class CreatedOrderRequestDto {


    private final Long userId;

    private final List<OrderMenu> orderMenus;

    public CreatedOrderRequestDto(List<OrderMenu> orderMenus, Long userId) {
        this.orderMenus = orderMenus;
        this.userId = userId;
    }

}
