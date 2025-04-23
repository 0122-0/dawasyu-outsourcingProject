package com.example.dawasyu.domain.user.service;

import com.example.dawasyu.common.jwt.JwtProvider;
import com.example.dawasyu.domain.user.dto.response.SignUpResponseDto;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;

    // 회원가입
    public SignUpResponseDto signUp (String email, String password, String name, String number, String nickname, String roadAddress, String detailAddress, String userRole) {
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(email, encodedPassword, name, number, nickname, roadAddress, detailAddress, userRole);

        User signUp = userRepository.save(user);

        return new SignUpResponseDto(signUp.getId(), signUp.getEmail(),signUp.getNickName(),signUp.getCreatedAt());
    }

    // 회원 탈퇴
    public void deleteUser (String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if(user.isDeleted()) {
            throw new RuntimeException("이미 탈퇴한 사용자입니다.");
        }

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        user.setDeleted(true);
        userRepository.save(user);
    }

}
