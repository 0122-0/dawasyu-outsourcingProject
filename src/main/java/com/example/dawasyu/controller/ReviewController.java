package com.example.dawasyu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dawasyu.common.annotation.LoginUser;
import com.example.dawasyu.common.reponseMessege.ResponseMessage;
import com.example.dawasyu.domain.review.dto.request.ReviewRequestDto;
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
}
