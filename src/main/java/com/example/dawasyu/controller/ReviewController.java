package com.example.dawasyu.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dawasyu.common.annotation.LoginUser;
import com.example.dawasyu.common.reponseMessege.ResponseMessage;
import com.example.dawasyu.domain.review.dto.request.ReviewDeleteRequestDto;
import com.example.dawasyu.domain.review.dto.request.ReviewRequestDto;
import com.example.dawasyu.domain.review.dto.request.ReviewUpdateRequestDto;
import com.example.dawasyu.domain.review.dto.response.ReviewResponseDto;
import com.example.dawasyu.domain.review.service.ReviewService;
import com.example.dawasyu.domain.user.entity.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	// 리뷰 생성
	@PostMapping("/orders/{orderId}/reviews")
	public ResponseEntity<ResponseMessage<ReviewResponseDto>> saveReview(
		@Valid @RequestBody ReviewRequestDto dto,
		@PathVariable Long orderId,
		@LoginUser User loginUser
	) {
		ReviewResponseDto responseDto = reviewService.saveReview(dto, orderId, loginUser.getId());

		ResponseMessage<ReviewResponseDto> responseMessage = ResponseMessage.<ReviewResponseDto>builder()
			.statusCode(HttpStatus.CREATED.value())
			.message("리뷰가 등록되었습니다.")
			.data(responseDto)
			.build();

		return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
	}

	// 리뷰 수정
	@PatchMapping("/orders/reviews/{reviewId}")
	public ResponseEntity<ResponseMessage<String>> updateReview(
		@Valid @RequestBody ReviewUpdateRequestDto dto,
		@PathVariable Long reviewId,
		@LoginUser User loginUser
	) {
		reviewService.updateReview(dto, reviewId, loginUser.getId());

		ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
			.statusCode(HttpStatus.OK.value())
			.message("리뷰가 성공적으로 수정되었습니다.")
			.data("Review updated successfully")
			.build();

		return ResponseEntity.ok(responseMessage);
	}

	// 리뷰 삭제
	@DeleteMapping("/orders/reviews/{reviewId}")
	public ResponseEntity<ResponseMessage<String>> deleteReview(
		@Valid @RequestBody ReviewDeleteRequestDto dto,
		@PathVariable Long reviewId,
		@LoginUser User loginUser
	) {
		reviewService.delete(dto.getPassword(), reviewId, loginUser.getId());

		ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
			.statusCode(HttpStatus.OK.value())
			.message("리뷰가 삭제되었습니다.")
			.data("Review deleted successfully")
			.build();

		return ResponseEntity.ok(responseMessage);
	}

	// 내가 작성한 리뷰 전체 조회
	@GetMapping("/users/me/reviews")
	public ResponseEntity<ResponseMessage<List<ReviewResponseDto>>> findAllReviewsById(@LoginUser User loginUser) {
		List<ReviewResponseDto> responseDtoList = reviewService.findAllReviewsById(loginUser.getId());

		String message = responseDtoList.isEmpty()
			? "작성한 리뷰가 없습니다."
			: loginUser.getNickName() + "님이 작성한 리뷰는 총 " + responseDtoList.size() + "개 입니다.";

		ResponseMessage<List<ReviewResponseDto>> responseMessage = ResponseMessage.<List<ReviewResponseDto>>builder()
			.statusCode(HttpStatus.OK.value())
			.message(message)
			.data(responseDtoList)
			.build();

		return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
	}

	// 상점 리뷰 전체 조회 + 별점 범위 조회
	@GetMapping("/stores/{storeId}/reviews")
	public ResponseEntity<ResponseMessage<List<ReviewResponseDto>>> findReviewsByStore(
		@PathVariable Long storeId,
		@RequestParam(required = false) Integer minRating,
		@RequestParam(required = false) Integer maxRating,
		@LoginUser User loginUser
	) {
		List<ReviewResponseDto> responseDtoList;
		String message;

		if (minRating != null && maxRating != null) {
			responseDtoList = reviewService.findReviewsByStoreAndRating(storeId, minRating, maxRating, loginUser.getId());
			message = "상점 리뷰를 별점 범위로 조회하는 데 성공했습니다.";
		} else {
			responseDtoList = reviewService.findAllReviewsByStoreId(storeId, loginUser.getId());
			message = "상점 리뷰 전체 조회에 성공했습니다.";
		}

		ResponseMessage<List<ReviewResponseDto>> responseMessage = ResponseMessage.<List<ReviewResponseDto>>builder()
			.statusCode(HttpStatus.OK.value())
			.message(message)
			.data(responseDtoList)
			.build();

		return ResponseEntity.ok(responseMessage);
	}

}
