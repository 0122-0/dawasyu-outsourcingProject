package com.example.dawasyu.controller;

import com.example.dawasyu.common.annotation.LoginUser;
import com.example.dawasyu.common.jwt.JwtProvider;
import com.example.dawasyu.common.reponseMessege.ResponseMessage;
import com.example.dawasyu.domain.review.dto.response.ReviewResponseDto;
import com.example.dawasyu.domain.user.dto.request.DeleteUserRequestDto;
import com.example.dawasyu.domain.user.dto.request.SignUpRequestDto;
import com.example.dawasyu.domain.user.dto.request.UpdatePasswordRequestDto;
import com.example.dawasyu.domain.user.dto.request.UserUpdateAddressRequestDto;
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
    public ResponseEntity<ResponseMessage<SignUpResponseDto>> signUp (@Valid @RequestBody SignUpRequestDto requestDto) {
        SignUpResponseDto signUpResponseDto = userService.signUp(
                requestDto.getEmail(),
                requestDto.getPassword(),
                requestDto.getName(),
                requestDto.getNumber(),
                requestDto.getNickname(),
                requestDto.getRoadAddress(),
                requestDto.getDetailAddress(),
                requestDto.getUserRole());

        ResponseMessage<SignUpResponseDto> responseMessage = ResponseMessage.<SignUpResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("회원가입이 완료되었습니다.")
                .data(signUpResponseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
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
    public ResponseEntity<ResponseMessage<UserResponseDto>> findUserById(@LoginUser User loginUser) {

        UserResponseDto userResponseDto = userService.findUserById(loginUser.getId());

        ResponseMessage<UserResponseDto> responseMessage = ResponseMessage.<UserResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("프로필 조회 성공")
                .data(userResponseDto)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    // 비밀번호 수정
    @PutMapping("/password")
    public ResponseEntity<ResponseMessage<Void>> updatePassword(@RequestBody UpdatePasswordRequestDto requestDto, @LoginUser User loginUser) {

        userService.updatePassword(loginUser.getId(), requestDto.getOldPassword(), requestDto.getNewPassword());

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("비밀번호가 성공적으로 수정되었습니다.")
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    // 주소 수정
    @PutMapping("/address")
    public ResponseEntity<ResponseMessage<UserResponseDto>> updateUserAddress(@RequestBody UserUpdateAddressRequestDto requestDto, @LoginUser User loginUser) {

        UserResponseDto responseDto = userService.updateAddress(loginUser.getId(), requestDto);

        ResponseMessage<UserResponseDto> responseMessage = ResponseMessage.<UserResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("주소가 성공적으로 수정되었습니다.")
                .data(responseDto)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }




}
