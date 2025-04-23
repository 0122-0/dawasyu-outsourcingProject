package com.example.dawasyu.controller;

import com.example.dawasyu.common.jwt.JwtProvider;
import com.example.dawasyu.domain.auth.dto.request.LoginRequestDto;
import com.example.dawasyu.domain.auth.dto.response.LoginResponseDto;
import com.example.dawasyu.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtProvider jwtProvider;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login (@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = authService.login(requestDto.getEmail(), requestDto.getPassword());
        return ResponseEntity.ok(response);
    }

//    // 로그아웃
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout (HttpServletRequest request) {
//        String accessToken = jwtProvider.resolveToken(request);
//
//        if(accessToken != null && jwtProvider.validateToken(accessToken)) {
//            Long userId = jwtProvider.getUserIdFromToken(accessToken);
//
//
//        }
//    }



}
