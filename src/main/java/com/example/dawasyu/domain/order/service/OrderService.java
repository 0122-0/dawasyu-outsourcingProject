package com.example.dawasyu.domain.order.service;


import com.example.dawasyu.domain.order.dto.response.CreatedOrderResponseDto;
import com.example.dawasyu.domain.order.dto.response.OrderResponseDto;
import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.orderMenu.entity.OrderMenu;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.repository.OrderRepository;
import com.example.dawasyu.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;


    public CreatedOrderResponseDto createOrder (List<OrderMenu> orderMenus, Long userId) {

        User findUser = userRepository.findUserById(userId);

        Order order = new Order(orderMenus, findUser);

        Long totalPrice = orderMenus.stream()
                .mapToLong(orderMenu -> orderMenu.getMenu().getPrice() * orderMenu.getQuantity())
                .sum();
        Order createdOrder = orderRepository.save(order);

        return new CreatedOrderResponseDto(generateOrderNumber(), createdOrder.getOrderMenus(), totalPrice , createdOrder.getCreatedAt());
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

