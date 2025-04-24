package com.example.dawasyu.domain.user.dto.response;

import com.example.dawasyu.domain.user.entity.UserRole;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final String email;

    private final String name;

    private final String number;

    private final String nickname;

    private final String roadAddress;

    private final String detailAddress;

    private final UserRole userRole;

    public UserResponseDto(String email, String name, String number, String nickname, String roadAddress, String detailAddress, UserRole userRole) {
        this.email = email;
        this.name = name;
        this.number = number;
        this.nickname = nickname;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.userRole = userRole;
    }
}
