package com.example.dawasyu.domain.store.dto.request;

import com.example.dawasyu.domain.store.entity.Store;
import com.example.dawasyu.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class RequestStoreDTO {
    private String name;
    private String number;
    private String bizNo;
    private String roadAddress;
    private String detailAddress;
    private int minPrice;
    private String openTime;
    private String closeTime;
    private List<String> categories;

    public Store toEntity(User owner) {
        return Store.builder()
                .name(this.name)
                .number(this.number)
                .bizNo(this.bizNo)
                .roadAddress(this.roadAddress)
                .detailAddress(this.detailAddress)
                .minPrice(this.minPrice)
                .openTime(LocalTime.parse(this.openTime))
                .closeTime(LocalTime.parse(this.closeTime))
                .owner(owner)
                .build();
    }
}
