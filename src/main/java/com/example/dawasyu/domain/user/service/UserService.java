package com.example.dawasyu.domain.user.service;

import com.example.dawasyu.common.error.ErrorCode;
import com.example.dawasyu.domain.user.dto.request.UserUpdateAddressRequestDto;
import com.example.dawasyu.domain.user.dto.response.SignUpResponseDto;
import com.example.dawasyu.domain.user.dto.response.UserResponseDto;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 회원가입
    public SignUpResponseDto signUp (String email,
                                     String password,
                                     String name,
                                     String number,
                                     String nickname,
                                     String roadAddress,
                                     String detailAddress,
                                     String userRole) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isDeleted()) {
                user.reactivate(encodedPassword, name, number, nickname, roadAddress, detailAddress, userRole);
                return new SignUpResponseDto(user.getId(), user.getEmail(), user.getNickName(), user.getCreatedAt());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorCode.EMAIL_DUPLICATION.getMessage());
            }
        }

        User user = new User(email, encodedPassword, name, number, nickname, roadAddress, detailAddress, userRole);

        User signUp = userRepository.save(user);

        return new SignUpResponseDto(signUp.getId(), signUp.getEmail(),signUp.getNickName(),signUp.getCreatedAt());
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser (String email, String password) {
        User user = userRepository.findByEmailAndDeletedFalseOrElseThrow(email);

        if(user.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorCode.EMAIL_DUPLICATION.getMessage());
        }

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorCode.PASSWORD_NOT_MATCHED.getMessage());
        }

        user.softDelete(); // 소프트 딜리티드
    }

    // 내 프로필 조회
    public UserResponseDto findUserById (Long userId) {
        User findUser = userRepository.findUserByIdOrElseThrow(userId);
        return new UserResponseDto(findUser.getEmail(), findUser.getName(), findUser.getNumber(), findUser.getNickName(), findUser.getRoadAddress(), findUser.getDetailAddress(), findUser.getUserRole());
    }

    // 비밀번호 수정
    public void updatePassword (Long userId, String oldPassword, String newPassword) {
        User findUser = userRepository.findUserByIdOrElseThrow(userId);

        // 1. 현재 비밀번호 일치하지 않음
        if (!passwordEncoder.matches(oldPassword, findUser.getPassword())) {         // 비밀번호가 해시 처리돼 있다면 PasswordEncoder.matches()를 사용해야 한다.
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // 2. 새 비밀번호가 기존과 같은 경우
        if (passwordEncoder.matches(newPassword, findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "현재 비밀번호와 다른 비밀번호를 입력해주세요.");
        }
        // 새로 입력받은 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(newPassword);
        findUser.updatePassword(encodedPassword);
    }

    // 주소 변경
    @Transactional
    public UserResponseDto updateAddress (Long userId, UserUpdateAddressRequestDto requestDto) {
        User findUser = userRepository.findUserByIdOrElseThrow(userId);

        findUser.updateAddress(requestDto.getRoadAddress(), requestDto.getDetailAddress());
        return new UserResponseDto(findUser.getEmail(), findUser.getName(), findUser.getNumber(), findUser.getNickName(), findUser.getRoadAddress(), findUser.getDetailAddress(), findUser.getUserRole());
    }
}
