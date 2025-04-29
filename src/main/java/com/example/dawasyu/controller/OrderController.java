package com.example.dawasyu.controller;


import com.example.dawasyu.common.annotation.LoginUser;
import com.example.dawasyu.common.reponseMessege.ResponseMessage;
import com.example.dawasyu.domain.order.dto.request.CreatedOrderRequestDto;
import com.example.dawasyu.domain.order.dto.request.OrderStatusRequestDto;
import com.example.dawasyu.domain.order.dto.response.CreatedOrderResponseDto;
import com.example.dawasyu.domain.order.dto.response.OrderResponseDto;
import com.example.dawasyu.domain.order.dto.response.OrderStatusResponseDto;
import com.example.dawasyu.domain.order.service.OrderService;
import com.example.dawasyu.domain.user.entity.User;
import jakarta.validation.Valid;
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
    public ResponseEntity<ResponseMessage<CreatedOrderResponseDto>> createOrder (@RequestBody @Valid CreatedOrderRequestDto requestDto, @PathVariable Long storeId, @LoginUser User loginUser)
    {
        CreatedOrderResponseDto responseDto = orderService.createOrder( requestDto,storeId, loginUser);

        ResponseMessage<CreatedOrderResponseDto> responseMessage = ResponseMessage.<CreatedOrderResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("주문이 등록되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
}

    @GetMapping("/stores/{storeId}/orders/{orderId}")
    public ResponseEntity<ResponseMessage<OrderResponseDto>> findOrderById (@PathVariable Long storeId,@PathVariable Long orderId, @LoginUser User loginUser)
    {
        OrderResponseDto findedById = orderService.findOrderById(storeId,orderId);

        ResponseMessage<OrderResponseDto> responseMessage = ResponseMessage.<OrderResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("주문이 조회되었습니다.")
                .data(findedById)
                .build();
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/orders")
    public ResponseEntity<ResponseMessage<List<OrderResponseDto>>> findAll (){

        List<OrderResponseDto> findAll = orderService.findAll();

        ResponseMessage<List<OrderResponseDto>> responseMessage = ResponseMessage.<List<OrderResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("전체 주문이 조회되었습니다.")
                .data(findAll)
                .build();

        return ResponseEntity.ok(responseMessage);
    }

    @PatchMapping("/stores/{storeId}/orders/{orderId}/status")

    public ResponseEntity<ResponseMessage<OrderStatusResponseDto>> changedStatus (
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

        ResponseMessage<OrderStatusResponseDto> responseMessage = ResponseMessage.<OrderStatusResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("주문상태 변경에 성공하셨습니다.")
                .data(changedStatus)
                .build();

        return ResponseEntity.ok(responseMessage);
    }
}
