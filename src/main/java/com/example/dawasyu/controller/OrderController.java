package com.example.dawasyu.controller;


import com.example.dawasyu.common.annotation.LoginUser;
import com.example.dawasyu.domain.order.dto.request.CreatedOrderRequestDto;
import com.example.dawasyu.domain.order.dto.response.CreatedOrderResponseDto;
import com.example.dawasyu.domain.order.dto.response.OrderResponseDto;
import com.example.dawasyu.domain.order.service.OrderService;
import com.example.dawasyu.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CreatedOrderResponseDto> createOrder (@RequestBody CreatedOrderRequestDto requestDto, @LoginUser User loginUser)
    {
        CreatedOrderResponseDto responseDto = orderService.createOrder(requestDto, loginUser);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

//    @GetMapping("/{orderId}")
//    public ResponseEntity<OrderResponseDto> findOrderById (@PathVariable Long OrderId)
//    {
//       OrderResponseDto findedById = orderService.findOrderById(OrderId);
//
//        return new ResponseEntity<>(findedById, HttpStatus.OK);
//    }


}
