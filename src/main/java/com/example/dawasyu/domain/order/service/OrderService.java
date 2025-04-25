package com.example.dawasyu.domain.order.service;


import com.example.dawasyu.domain.menu.entity.Menu;
import com.example.dawasyu.domain.order.dto.request.CreatedOrderRequestDto;
import com.example.dawasyu.domain.order.dto.response.CreatedOrderResponseDto;

import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.order.entity.OrderStatus;
import com.example.dawasyu.domain.orderMenu.entity.OrderMenu;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.repository.MenuRepository;
import com.example.dawasyu.repository.OrderRepository;
import com.example.dawasyu.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final MenuRepository menuRepository;

    @Transactional
    public CreatedOrderResponseDto createOrder (CreatedOrderRequestDto requestDto, User loginUser) {

         Long totalPrice = requestDto.getMenus().stream()
                .mapToLong(menuDto -> {
                    Menu menu = menuRepository.findById(menuDto.getMenuId())
                            .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다: " + menuDto.getMenuId()));
                    return menu.getPrice() * menuDto.getQuantity();
                })
                .sum();

        List<OrderMenu> orderMenuList = requestDto.getMenus().stream().map(menuDto ->{
                Menu menu = menuRepository.findMenuByIdOrElseThrow(menuDto.getMenuId());
            // OrderMenu 객체를 생성하고, 수량 설정
            OrderMenu orderMenu = new OrderMenu();
            orderMenu.setMenu(menu); // Menu 객체 설정
            orderMenu.setQuantity(menuDto.getQuantity()); // 수량 설정

            return orderMenu;
        }).collect(Collectors.toList());

        Order order = new Order(totalPrice, generateOrderNumber(), loginUser, orderMenuList);

        Order createdOrder = orderRepository.save(order);

        return new CreatedOrderResponseDto(createdOrder);
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

