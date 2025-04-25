package com.example.dawasyu.domain.auth.service;

import com.example.dawasyu.common.jwt.JwtProvider;
import com.example.dawasyu.common.jwt.dto.JwtDto;
import com.example.dawasyu.domain.auth.dto.response.LoginResponseDto;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 로그인
    public LoginResponseDto login (String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (user.isDeleted()) {
            throw new RuntimeException("탈퇴한 계정입니다.");
        }

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

        JwtDto token = jwtProvider.generateToken(user);

        return new LoginResponseDto(user.getId(), token.getAccessToken(), user.getEmail(), user.getNickName());
    }

    // 로그아웃




}
