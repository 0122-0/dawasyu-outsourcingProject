package com.example.dawasyu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dawasyu.common.error.CustomException;
import com.example.dawasyu.common.error.ErrorCode;
import com.example.dawasyu.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	default Review findByIdOrElseThrow(Long reviewId){
		return findById(reviewId).orElseThrow(()->new CustomException(ErrorCode.REVIEW_NOT_FOUND));
	}

	boolean existsByOrderId(Long orderId);

	List<Review> findAllByUserId(Long userId);
}
