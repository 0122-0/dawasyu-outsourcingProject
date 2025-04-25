package com.example.dawasyu.domain.review.service;

import org.springframework.stereotype.Service;

import com.example.dawasyu.common.error.CustomException;
import com.example.dawasyu.common.error.ErrorCode;
import com.example.dawasyu.domain.menu.entity.Menu;
import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.review.dto.request.ReviewRequestDto;
import com.example.dawasyu.domain.review.dto.response.ReviewResponseDto;
import com.example.dawasyu.domain.review.entity.Review;
import com.example.dawasyu.domain.store.entity.Store;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.repository.MenuRepository;
import com.example.dawasyu.repository.OrderRepository;
import com.example.dawasyu.repository.ReviewRepository;
import com.example.dawasyu.repository.StoreRepository;
import com.example.dawasyu.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final MenuRepository menuRepository;
	private final StoreRepository storeRepository;

	public ReviewResponseDto saveReview(ReviewRequestDto dto, Long orderId, Long userId) {

		// 해당 주문, 리뷰작성자 관련 데이터 호출
		User user = userRepository.findUserByIdOrElseThrow(userId);
		Order order = orderRepository.findOrderByIdOrElseThrow(orderId);
		Menu menu = menuRepository.findMenuByIdOrElseThrow(dto.getMenuId());
		Store store = storeRepository.findStoreByIdOrElseThrow(dto.getStoreId());

		// 유저 본인 확인
		if (!order.getUser().getId().equals(user.getId())) {
			throw new CustomException(ErrorCode.NOT_ORDER_OWNER);
		}

		// 중복 리뷰 방지
		if (reviewRepository.existsByOrderId(orderId)) {
			throw new CustomException(ErrorCode.ALREADY_REVIEWED);
		}

		// 리뷰 생성
		Review reviewToSave = dto.toEntity(order, user, store, menu);

		Review saved = reviewRepository.save(reviewToSave);

		return ReviewResponseDto.toDto(saved);
	}
}
