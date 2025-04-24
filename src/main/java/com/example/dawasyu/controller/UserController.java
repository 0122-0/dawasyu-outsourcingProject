package com.example.dawasyu.controller;

import com.example.dawasyu.common.annotation.LoginUser;
import com.example.dawasyu.common.jwt.JwtProvider;
import com.example.dawasyu.domain.user.dto.request.DeleteUserRequestDto;
import com.example.dawasyu.domain.user.dto.request.SignUpRequestDto;
import com.example.dawasyu.domain.user.dto.request.UpdatePasswordRequestDto;
import com.example.dawasyu.domain.user.dto.response.SignUpResponseDto;
import com.example.dawasyu.domain.user.dto.response.UserResponseDto;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final JwtProvider jwtProvider;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp (@Valid @RequestBody SignUpRequestDto requestDto) {
        SignUpResponseDto signUpResponseDto = userService.signUp(
                requestDto.getEmail(),
                requestDto.getPassword(),
                requestDto.getName(),
                requestDto.getNumber(),
                requestDto.getNickname(),
                requestDto.getRoadAddress(),
                requestDto.getDetailAddress(),
                requestDto.getUserRole());

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    // 회원탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody DeleteUserRequestDto deleteUserRequestDto, @LoginUser User loginUser) {
        // 로그인된 사용자 정보가 없으면 401 Unauthorized 응답
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        try {
            // 비밀번호를 이용해 회원탈퇴 진행
            userService.deleteUser(loginUser.getEmail(), deleteUserRequestDto.getPassword());
            return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
        } catch (RuntimeException e) {
            // 예외 메시지를 응답으로 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    // 나의 프로필 조회
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> findUserById (@LoginUser User loginUser ) {

        UserResponseDto userResponseDto = userService.findUserById(loginUser.getId());

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 비밀번호 수정
    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdatePasswordRequestDto requestDto, @LoginUser User loginUser) {
        userService.updatePassword(loginUser.getId(), requestDto.getOldPassword(), requestDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
