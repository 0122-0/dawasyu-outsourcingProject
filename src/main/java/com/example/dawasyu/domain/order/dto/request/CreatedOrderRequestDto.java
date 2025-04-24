package com.example.dawasyu.domain.order.dto.request;

import com.example.dawasyu.domain.orderMenu.entity.OrderMenu;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class CreatedOrderRequestDto {


    private final List<OrderMenu> orderMenus;

    public CreatedOrderRequestDto(List<OrderMenu> orderMenus) {
        this.orderMenus = orderMenus;
    }

}
