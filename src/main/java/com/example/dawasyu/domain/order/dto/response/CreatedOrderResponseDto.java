package com.example.dawasyu.domain.order.dto.response;

import com.example.dawasyu.domain.orderMenu.entity.OrderMenu;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreatedOrderResponseDto {


//    private final List<OrderMenu> orderMenus;

    private final Long totalPrice;

    private final LocalDateTime createdAt;

    private final String orderNumber;

    public CreatedOrderResponseDto(String orderNumber,  Long totalPrice, LocalDateTime createdAt) {

        this.orderNumber = orderNumber;
//        this.orderMenus = orderMenus;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;

    }


}
