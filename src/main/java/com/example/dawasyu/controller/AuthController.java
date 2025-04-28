package com.example.dawasyu.controller;

import com.example.dawasyu.common.jwt.JwtProvider;
import com.example.dawasyu.common.reponseMessege.ResponseMessage;
import com.example.dawasyu.domain.auth.dto.request.LoginRequestDto;
import com.example.dawasyu.domain.auth.dto.response.LoginResponseDto;
import com.example.dawasyu.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseMessage<LoginResponseDto>> login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = authService.login(requestDto.getEmail(), requestDto.getPassword());

        ResponseMessage<LoginResponseDto> responseMessage = ResponseMessage.<LoginResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("로그인 성공")
                .data(response)
                .build();

        return ResponseEntity.ok(responseMessage);
    }



}
