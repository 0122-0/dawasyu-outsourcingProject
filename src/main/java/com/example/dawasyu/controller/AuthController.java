package com.example.dawasyu.controller;

import com.example.dawasyu.domain.auth.dto.request.LoginRequestDto;
import com.example.dawasyu.domain.auth.dto.response.LoginResponseDto;
import com.example.dawasyu.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = authService.login(requestDto.getEmail(), requestDto.getPassword());

        return ResponseEntity.ok(response);
    }
}
