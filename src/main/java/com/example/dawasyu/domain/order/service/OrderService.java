package com.example.dawasyu.domain.order.service;


import com.example.dawasyu.domain.order.dto.response.CreatedOrderResponseDto;
import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.orderMenu.entity.OrderMenu;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

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

    //주문 샏성 x, 주문하기 o
    public CreatedOrderResponseDto createOrder (List<OrderMenu> orderMenus, Long userId) {

        User findUser = userRepository.findById(userId);

        Order order = new Order(orderMenus, findUser);
        Order createdOrder = orderRepository.save(order);

        int totalPrice = orderMenus.stream()
                .mapToInt(orderMenu -> orderMenu.getMenu().getPrice())
                .sum();


        return new CreatedOrderResponseDto(createdOrder.getOrderMenus(), totalPrice , createdOrder.getCreatedAt());
    }

//    public static String generateOrderNumber() {
//       String timePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//         int randomPart = new Random().nextInt(900000) + 100000; // 5자리 난수
//         return "DWS" + timePart + "-" + randomPart;
//    }

}
