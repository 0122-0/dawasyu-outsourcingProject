package com.example.dawasyu.domain.review.dto.response;

import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.review.entity.Review;
import com.example.dawasyu.domain.store.dto.response.ResponseStoreDTO;
import com.example.dawasyu.domain.store.entity.Store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewResponseDto {
	private Long id;
	private String content;
	private int rating;

	public static ReviewResponseDto toDto(Review review) {
		return ReviewResponseDto.builder()
			.id(review.getId())
			.content(review.getContent())
			.rating(review.getRating())
			.build();
	}
}
