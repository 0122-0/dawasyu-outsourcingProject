package com.example.dawasyu.domain.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {

    private Long menuId;
    private int quantity;
}
