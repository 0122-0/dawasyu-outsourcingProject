package com.example.dawasyu.controller;


import com.example.dawasyu.common.annotation.LoginUser;
import com.example.dawasyu.domain.order.dto.request.CreatedOrderRequestDto;
import com.example.dawasyu.domain.order.dto.request.OrderStatusRequestDto;
import com.example.dawasyu.domain.order.dto.response.CreatedOrderResponseDto;
import com.example.dawasyu.domain.order.dto.response.OrderResponseDto;
import com.example.dawasyu.domain.order.dto.response.OrderStatusResponseDto;
import com.example.dawasyu.domain.order.entity.OrderStatus;
import com.example.dawasyu.domain.order.service.OrderService;
import com.example.dawasyu.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Aspect
public class OrderController {

    private final OrderService orderService;
    //@LoginUser user 값이 spring 안에 있는 유저로직이 토큰을 받아서 login 유저가 누구인지 알려준다.
    @PostMapping("/stores/{storeId}/orders")
    public ResponseEntity<CreatedOrderResponseDto> createOrder (@RequestBody @Valid CreatedOrderRequestDto requestDto,@PathVariable Long storeId, @LoginUser User loginUser)
    {
        CreatedOrderResponseDto responseDto = orderService.createOrder( requestDto,storeId, loginUser);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    @GetMapping("/stores/{storeId}/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> findOrderById (@PathVariable Long storeId,@PathVariable Long orderId, @LoginUser User loginUser)
    {
       OrderResponseDto findedById = orderService.findOrderById(storeId,orderId);

        return new ResponseEntity<>(findedById, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> findAll (){

        List<OrderResponseDto> findAll = orderService.findAll();

        return new ResponseEntity<>(findAll, HttpStatus.OK);
    }

    @PatchMapping("/stores/{storeId}/orders/{orderId}/status")

    public ResponseEntity<OrderStatusResponseDto> changedStatus (
            @PathVariable Long storeId,
            @PathVariable Long orderId,
            @RequestBody OrderStatusRequestDto requestDto,
            @LoginUser User loginUser)
    {
        OrderStatusResponseDto changedStatus = orderService.changedStatus(
                storeId,
                orderId,
                requestDto.getOldOrderStatus(),
                requestDto.getNewOrderStatus()
        );
        return new ResponseEntity<>(changedStatus, HttpStatus.OK);
    }
}
