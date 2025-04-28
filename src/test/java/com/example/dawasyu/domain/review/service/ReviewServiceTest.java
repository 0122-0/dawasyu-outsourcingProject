package com.example.dawasyu.domain.review.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;

import com.example.dawasyu.common.error.CustomException;
import com.example.dawasyu.common.error.ErrorCode;
import com.example.dawasyu.domain.menu.entity.Menu;
import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.order.entity.OrderStatus;
import com.example.dawasyu.domain.review.dto.request.ReviewRequestDto;
import com.example.dawasyu.domain.review.entity.Review;
import com.example.dawasyu.domain.store.entity.Store;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ReviewServiceTest {

	@InjectMocks
	private ReviewService reviewService;

	@Mock
	private ReviewRepository reviewRepository;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private MenuRepository menuRepository;

	@Mock
	private StoreRepository storeRepository;

	@Mock
	private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

	private User user;
	private Order order;
	private Menu menu;
	private Store store;
	private ReviewRequestDto reviewRequestDto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		user = User.builder()
			.id(1L)
			.password("encodedPassword")
			.build();

		order = Order.builder()
			.id(1L)
			.user(user)
			.orderStatus(OrderStatus.COMPLETED)
			.orderMenus(new ArrayList<>())
			.build();

		menu = Menu.builder()
			.id(1L)
			.build();

		store = Store.builder()
			.id(1L)
			.build();

		reviewRequestDto = ReviewRequestDto.builder()
			.storeId(store.getId())
			.menuId(menu.getId())
			.content("맛있어요")
			.rating(5)
			.build();
	}

	@Test
	void saveReview_성공() {
		// given
		when(userRepository.findUserByIdOrElseThrow(1L)).thenReturn(user);
		when(orderRepository.findOrderByIdOrElseThrow(1L)).thenReturn(order);
		when(menuRepository.findMenuByIdOrElseThrow(1L)).thenReturn(menu);
		when(storeRepository.findStoreByIdOrElseThrow(1L)).thenReturn(store);
		when(reviewRepository.existsByOrderId(1L)).thenReturn(false);

		Review savedReview = Review.builder()
			.id(1L)
			.content("맛있어요")
			.rating(5)
			.user(user)
			.order(order)
			.menu(menu)
			.store(store)
			.build();

		when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

		// when
		var response = reviewService.saveReview(reviewRequestDto, 1L, 1L);

		// then
		assertThat(response).isNotNull();
		assertThat(response.getContent()).isEqualTo("맛있어요");
		assertThat(response.getRating()).isEqualTo(5);
	}

	@Test
	void saveReview_본인_주문이_아닐때_예외발생() {
		// given
		User otherUser = User.builder().id(2L).build();
		order = Order.builder()
			.id(1L)
			.user(otherUser)
			.orderStatus(OrderStatus.COMPLETED)
			.build();

		when(userRepository.findUserByIdOrElseThrow(1L)).thenReturn(user);
		when(orderRepository.findOrderByIdOrElseThrow(1L)).thenReturn(order);

		// when & then
		assertThatThrownBy(() -> reviewService.saveReview(reviewRequestDto, 1L, 1L))
			.isInstanceOf(CustomException.class)
			.hasMessageContaining(ErrorCode.NOT_ORDER_OWNER.getMessage());
	}

	@Test
	void saveReview_이미_리뷰가_존재할때_예외발생() {
		// given
		when(userRepository.findUserByIdOrElseThrow(1L)).thenReturn(user);
		when(orderRepository.findOrderByIdOrElseThrow(1L)).thenReturn(order);
		when(menuRepository.findMenuByIdOrElseThrow(1L)).thenReturn(menu);
		when(storeRepository.findStoreByIdOrElseThrow(1L)).thenReturn(store);
		when(reviewRepository.existsByOrderId(1L)).thenReturn(true);

		// when & then
		assertThatThrownBy(() -> reviewService.saveReview(reviewRequestDto, 1L, 1L))
			.isInstanceOf(CustomException.class)
			.hasMessageContaining(ErrorCode.ALREADY_REVIEWED.getMessage());
	}

	@Test
	void saveReview_배달완료가_아닐때_예외발생() {
		// given
		order = Order.builder()
			.id(1L)
			.user(user)
			.orderStatus(OrderStatus.DELIVERING)
			.build();

		when(userRepository.findUserByIdOrElseThrow(1L)).thenReturn(user);
		when(orderRepository.findOrderByIdOrElseThrow(1L)).thenReturn(order);
		when(menuRepository.findMenuByIdOrElseThrow(1L)).thenReturn(menu);
		when(storeRepository.findStoreByIdOrElseThrow(1L)).thenReturn(store);
		when(reviewRepository.existsByOrderId(1L)).thenReturn(false);

		// when & then
		assertThatThrownBy(() -> reviewService.saveReview(reviewRequestDto, 1L, 1L))
			.isInstanceOf(CustomException.class)
			.hasMessageContaining(ErrorCode.ORDER_NOT_COMPLETED.getMessage());
	}
}
