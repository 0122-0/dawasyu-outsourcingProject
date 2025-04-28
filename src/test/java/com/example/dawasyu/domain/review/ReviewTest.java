package com.example.dawasyu.domain.review;

import static com.example.dawasyu.domain.order.service.OrderService.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.xml.transform.Result;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.dawasyu.domain.menu.entity.Menu;
import com.example.dawasyu.domain.order.service.OrderService;
import com.example.dawasyu.domain.review.entity.Review;
import com.example.dawasyu.domain.store.entity.Store;

public class ReviewTest {

	// MenuStatus가 완성되지 않았기 때문에 임의로 설정
	enum TestMenuStatus {
		ACTIVE, DELETED
	}

	// @Test
	// void 리뷰_생성_테스트() {
	//
	// 	// 1. 스토어 생성
	// 	Store store = new Store();
	// 	ReflectionTestUtils.setField(store, "id", 1L);
	// 	ReflectionTestUtils.setField(store, "name", "맛있는 김밥집");
	// 	ReflectionTestUtils.setField(store, "number", "010-1234-5678");
	// 	ReflectionTestUtils.setField(store, "bizNo", "123-45-67890");
	// 	ReflectionTestUtils.setField(store, "roadAddress", "서울시 강남구");
	// 	ReflectionTestUtils.setField(store, "detailAddress", "101호");
	// 	ReflectionTestUtils.setField(store, "minPrice", 10000);
	// 	ReflectionTestUtils.setField(store, "openTime", LocalTime.of(9, 0));
	// 	ReflectionTestUtils.setField(store, "closeTime", LocalTime.of(21, 0));
	// 	ReflectionTestUtils.setField(store, "deletedAt", LocalTime.of(22, 0));
	//
	// 	// 2. 메뉴 생성
	// 	Menu menu = new Menu();
	// 	ReflectionTestUtils.setField(menu, "id", 1L);
	// 	ReflectionTestUtils.setField(menu, "name", "참치김밥");
	// 	ReflectionTestUtils.setField(menu, "explain", "고소한 참치와 고소한 마요");
	// 	ReflectionTestUtils.setField(menu, "price", 4500);
	// 	// enum을 아직 정의 안 했으므로, 임시 enum 사용 불가.
	// 	// 따라서 menuStatus는 생략하거나 나중에 정의 후 사용
	//
	// 	// 3. 리뷰 생성
	// 	Review review = new Review();
	// 	ReflectionTestUtils.setField(review, "id", 1L);
	// 	ReflectionTestUtils.setField(review, "content", "정말 맛있어요!");
	// 	ReflectionTestUtils.setField(review, "rating", 5);
	// 	ReflectionTestUtils.setField(review, "store", store);
	// 	ReflectionTestUtils.setField(review, "menu", menu);
	//
	// 	// 검증
	// 	assertEquals("맛있는 김밥집", review.getStore().getName());
	// 	assertEquals("참치김밥", review.getMenu().getName());
	// }



	@Test
	void generateOrderNumber_주문번호_난수_생성_확인() {
		String orderNumber = OrderService.generateOrderNumber();

		System.out.println("생성된 주문번호: " + orderNumber);

		// 형식 검사: "DWS20250422183015-123456" 같은 형태인지
		assertTrue(orderNumber.matches("^DWS\\d{14}-\\d{6}$"));
	}
}
