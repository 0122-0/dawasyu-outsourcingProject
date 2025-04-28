package com.example.dawasyu.domain.review.dto.request;

import java.time.LocalTime;

import com.example.dawasyu.domain.menu.entity.Menu;
import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.review.entity.Review;
import com.example.dawasyu.domain.store.entity.Store;
import com.example.dawasyu.domain.user.entity.User;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReviewRequestDto {

	@NotBlank(message = "리뷰내용이 없습니다.")
	@Size(max = 1000, message = "리뷰는 1000자를 초과할 수 없습니다.")
	private final String content;

	@NotNull(message = "리뷰의 별점을 평가해주세요.")
	@Min(1)
	@Max(5)
	private final int rating;

	private final Long storeId;

	private final Long menuId;

	public Review toEntity(Order order, User user, Store store, Menu menu) {
		return Review.builder()
				.content(content)
				.rating(rating)
				.order(order)
				.user(user)
				.store(store)
				.menu(menu)
				.build();
	}
}
