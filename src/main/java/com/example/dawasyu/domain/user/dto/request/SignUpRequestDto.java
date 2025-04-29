package com.example.dawasyu.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    @NotBlank(message = "Email은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하이어야 합니다.")
    private final String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private final String name;

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private final String number;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private final String nickname;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private final String roadAddress;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private final String detailAddress;

    @NotBlank(message = "유저 유형은 필수 입력 값입니다.")
    private final String userRole;

    public SignUpRequestDto(String email, String password, String name, String number, String nickname, String roadAddress, String detailAddress, String userRole) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.number = number;
        this.nickname = nickname;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.userRole = userRole;
    }
}
