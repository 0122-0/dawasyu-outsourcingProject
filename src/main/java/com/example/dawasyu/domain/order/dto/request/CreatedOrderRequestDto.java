package com.example.dawasyu.domain.order.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CreatedOrderRequestDto {

    private final List<OrderRequestDto> menus;

    public CreatedOrderRequestDto(List<OrderRequestDto> menus) {
        this.menus = menus;
    }
}
