package com.example.dawasyu.domain.review.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.w3c.dom.stylesheets.LinkStyle;

import com.example.dawasyu.domain.menu.entity.Menu;
import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.orderMenu.dto.response.OrderMenuResponseDto;
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
	private ResponseStoreDTO store;
	private List<OrderMenuResponseDto> menus;

	public static ReviewResponseDto toDto(Review review) {
		List<OrderMenuResponseDto> menus = review.getOrder().getOrderMenus().stream()
			.map(OrderMenuResponseDto::new)
			.collect(Collectors.toList());

		return ReviewResponseDto.builder()
			.id(review.getId())
			.store(ResponseStoreDTO.toDto(review.getStore()))
			.menus(menus)
			.content(review.getContent())
			.rating(review.getRating())
			.build();
	}
}
