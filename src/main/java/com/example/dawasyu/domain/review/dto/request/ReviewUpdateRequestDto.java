package com.example.dawasyu.domain.review.dto.request;

import com.example.dawasyu.domain.review.entity.Review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewUpdateRequestDto {

	@NotBlank(message = "리뷰내용이 없습니다.")
	@Size(max = 1000, message = "리뷰는 1000자를 초과할 수 없습니다.")
	private final String content;

	@NotNull(message = "리뷰의 별점을 평가해주세요.")
	@Min(1)
	@Max(5)
	private final int rating;

	public Review toEntity() {
		return Review.builder()
				.content(content)
				.rating(rating)
				.build();
	}
}
