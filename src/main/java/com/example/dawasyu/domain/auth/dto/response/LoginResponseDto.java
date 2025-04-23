package com.example.dawasyu.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final Long id;

    private final String bearerToken;

    private final String email;

    private final String nickname;

    public LoginResponseDto(Long id, String bearerToken, String email, String nickname) {
        this.id = id;
        this.bearerToken = bearerToken;
        this.email = email;
        this.nickname = nickname;
    }
}
