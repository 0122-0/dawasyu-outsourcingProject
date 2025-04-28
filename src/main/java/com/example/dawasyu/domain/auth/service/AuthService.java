package com.example.dawasyu.domain.auth.service;

import com.example.dawasyu.common.error.ErrorCode;
import com.example.dawasyu.common.jwt.JwtProvider;
import com.example.dawasyu.common.jwt.dto.JwtDto;
import com.example.dawasyu.domain.auth.dto.response.LoginResponseDto;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        User user = userRepository.findByEmailAndDeletedFalseOrElseThrow(email);

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorCode.PASSWORD_NOT_MATCHED.getMessage());
        }

        JwtDto token = jwtProvider.generateToken(user);

        return new LoginResponseDto(user.getId(), token.getAccessToken(), user.getEmail(), user.getNickName());
    }

}
