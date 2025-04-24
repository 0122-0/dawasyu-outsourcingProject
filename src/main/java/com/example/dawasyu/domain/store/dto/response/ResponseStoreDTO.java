package com.example.dawasyu.domain.store.dto.response;

import com.example.dawasyu.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
public class ResponseStoreDTO {
    private Long id;
    private String name;
    private String number;
    private String bizNo;
    private String roadAddress;
    private String detailAddress;
    private int minPrice;
    private LocalTime openTime;
    private LocalTime closeTime;

    public static ResponseStoreDTO toDto(Store store) {
        return ResponseStoreDTO.builder()
                .id(store.getId())
                .name(store.getName())
                .number(store.getNumber())
                .bizNo(store.getBizNo())
                .roadAddress(store.getRoadAddress())
                .detailAddress(store.getDetailAddress())
                .minPrice(store.getMinPrice())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .build();
    }
}
