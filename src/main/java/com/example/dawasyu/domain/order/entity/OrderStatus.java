package com.example.dawasyu.domain.order.entity;

public enum OrderStatus {
    REQUESTED, // 주문 요청
    ACCEPTED, // 상점에서 주문을 수락
    DELIVERING, // 배달중
    COMPLETED // 배달완료
}
