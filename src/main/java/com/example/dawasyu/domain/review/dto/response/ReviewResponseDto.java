package com.example.dawasyu.domain.review.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.example.dawasyu.domain.orderMenu.dto.response.OrderMenuResponseDto;
import com.example.dawasyu.domain.review.entity.Review;
import com.example.dawasyu.domain.store.dto.response.StoreResponseDTO;

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
	private StoreResponseDTO store;
	private List<OrderMenuResponseDto> menus;

	public static ReviewResponseDto toDto(Review review) {
		List<OrderMenuResponseDto> menus = review.getOrder().getOrderMenus().stream()
			.map(OrderMenuResponseDto::new)
			.collect(Collectors.toList());

		return ReviewResponseDto.builder()
			.id(review.getId())
			.store(StoreResponseDTO.toDto(review.getStore()))
			.menus(menus)
			.content(review.getContent())
			.rating(review.getRating())
			.build();
	}
}
