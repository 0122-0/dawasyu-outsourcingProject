package com.example.dawasyu.domain.user.dto.request;

import lombok.Getter;

@Getter
public class UserUpdateAddressRequestDto {
    private final String roadAddress;
    private final String detailAddress;

    public UserUpdateAddressRequestDto(String roadAddress, String detailAddress) {
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
    }
}
