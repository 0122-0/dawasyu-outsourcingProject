package com.example.dawasyu.domain.review.dto.request;

import com.example.dawasyu.domain.review.entity.Review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewDeleteRequestDto {

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,16}$", message = "비밀번호는 8 ~ 16자의 영문 대소문자, 숫자, 특수문자로 이루어져야 합니다.")
	private final String password;

	public Review toEntity() {
		return Review.builder()

				.build();
	}
}
