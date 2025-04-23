package com.example.dawasyu.domain.user.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpResponseDto {

    private final Long id;

    private final String email;

    private final String nickname;

    private final LocalDateTime createdAt;


    public SignUpResponseDto(Long id, String email, String nickname, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }
}
