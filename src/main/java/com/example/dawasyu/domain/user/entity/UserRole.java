package com.example.dawasyu.domain.user.entity;

import java.util.Arrays;

public enum UserRole {

    USER, OWNER;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("유효하지 않은 UerRole"));
    }
}
