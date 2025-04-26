package com.example.dawasyu.domain.review.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.dawasyu.common.error.CustomException;
import com.example.dawasyu.common.error.ErrorCode;
import com.example.dawasyu.domain.menu.entity.Menu;
import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.order.entity.OrderStatus;
import com.example.dawasyu.domain.review.dto.request.ReviewRequestDto;
import com.example.dawasyu.domain.review.dto.request.ReviewUpdateRequestDto;
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
	private final PasswordEncoder passwordEncoder;

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

		// 배달 완료인지 확인
		if (!order.getOrderStatus().equals(OrderStatus.COMPLETED)) {
			throw new CustomException(ErrorCode.ORDER_NOT_COMPLETED);
		}

		// 리뷰 생성
		Review reviewToSave = dto.toEntity(order, user, store, menu);

		return ReviewResponseDto.toDto(reviewToSave);
	}

	@Transactional
	public void updateReview(ReviewUpdateRequestDto dto, Long reviewId, Long userId) {

		Review findReview = reviewRepository.findByIdOrElseThrow(reviewId);

		// 유저 본인 확인
		if (!findReview.getUser().getId().equals(userId)) {
			throw new CustomException(ErrorCode.USER_NOT_MATCHED);
		}

		findReview.updateReview(dto.getContent(), dto.getRating());

	}

	public void delete(String password, Long reviewId, Long userId) {

		Review findReview = reviewRepository.findByIdOrElseThrow(reviewId);

		// 비밀번호 검증
		if (!passwordEncoder.matches(password, findReview.getUser().getPassword())) {         // 비밀번호가 해시 처리돼 있다면 PasswordEncoder.matches()를 사용해야 한다.
			throw new CustomException(ErrorCode.PASSWORD_NOT_MATCHED);
		}

		// 내 리뷰인지 확인
		if (!findReview.getUser().getId().equals(userId)) {
			throw new CustomException(ErrorCode.USER_NOT_MATCHED);
		}

		reviewRepository.delete(findReview);
	}


	public List<ReviewResponseDto> findAllReviewsById(Long userId) {

		// User user = userRepository.findUserByIdOrElseThrow(userId);
		List<Review> reviews = reviewRepository.findAllByUserId(userId);

		return reviews.stream()
			.map(ReviewResponseDto::toDto)
			.collect(Collectors.toList());
	}
}
