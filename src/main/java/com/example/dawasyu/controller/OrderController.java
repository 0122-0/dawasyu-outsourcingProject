package com.example.dawasyu.controller;


import com.example.dawasyu.domain.order.dto.request.CreatedOrderRequestDto;
import com.example.dawasyu.domain.order.dto.response.CreatedOrderResponseDto;
import com.example.dawasyu.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{userId}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

//    @PostMapping
//    public ResponseEntity<CreatedOrderResponseDto> createOrder (@RequestBody CreatedOrderRequestDto requestDto, @PathVariable Long userId)
//    {
//        CreatedOrderResponseDto responseDto = orderService.createOrder(requestDto, userId);
//
//        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
//    }


}
