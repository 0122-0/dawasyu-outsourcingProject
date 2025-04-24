package com.example.dawasyu.domain.order.service;


import com.example.dawasyu.domain.order.dto.request.CreatedOrderRequestDto;
import com.example.dawasyu.domain.order.dto.response.CreatedOrderResponseDto;

import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.order.entity.OrderStatus;
import com.example.dawasyu.domain.orderMenu.entity.OrderMenu;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.repository.OrderRepository;
import com.example.dawasyu.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Random;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    @Transactional
    public CreatedOrderResponseDto createOrder (CreatedOrderRequestDto requestDto, User loginUser) {

        Long totalPrice = requestDto.getMenus().stream()
                .mapToLong(Menus -> Menus.getPrice() * Menus.getQuantity())
                .sum();

        Order order = new Order(totalPrice, generateOrderNumber(), loginUser);
        //gpt의 도움을 받아서 잘모름

        Order createdOrder = orderRepository.save(order);

//        // 오더 아이디랑 메뉴 아이디를 오더메뉴 객체에 넣기
//        for (OrderMenu orderMenu : requestDto.getOrderMenus()) {
//
//        }

        return new CreatedOrderResponseDto(createdOrder.getOrderNumber(), createdOrder.getTotalPrice(), createdOrder.getCreatedAt());
    }

    public static String generateOrderNumber() {
       String timePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmss"));
         int randomPart = new Random().nextInt(900000) + 100000;
         return "DWS" + timePart + "-" + randomPart;
    }

//    public ResponseEntity<OrderResponseDto> findOrderById (Long orderId){
//
//      Order findOrder = orderRepository.findOrderById(orderId);
//
//        return new OrderResponseDto();
//    }


}

