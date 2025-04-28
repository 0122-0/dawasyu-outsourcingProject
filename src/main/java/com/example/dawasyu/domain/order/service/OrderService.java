package com.example.dawasyu.domain.order.service;


import com.example.dawasyu.common.error.CustomException;
import com.example.dawasyu.common.error.ErrorCode;
import com.example.dawasyu.domain.menu.entity.Menu;
import com.example.dawasyu.domain.menu.entity.MenuStatus;
import com.example.dawasyu.domain.order.dto.request.CreatedOrderRequestDto;
import com.example.dawasyu.domain.order.dto.response.CreatedOrderResponseDto;

import com.example.dawasyu.domain.order.dto.response.OrderResponseDto;
import com.example.dawasyu.domain.order.dto.response.OrderStatusResponseDto;
import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.order.entity.OrderStatus;
import com.example.dawasyu.domain.orderMenu.entity.OrderMenu;
import com.example.dawasyu.domain.store.entity.Store;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.repository.MenuRepository;
import com.example.dawasyu.repository.OrderRepository;
import com.example.dawasyu.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public CreatedOrderResponseDto createOrder (CreatedOrderRequestDto requestDto,Long storeId, User loginUser) {

        Store store = storeRepository.findStoreByIdOrElseThrow(storeId);
        LocalTime now = LocalTime.now(); // 현재 시각 (시간, 분까지만)
        LocalTime openTime = store.getOpenTime(); // 가게 오픈 시간
        LocalTime closeTime = store.getCloseTime(); // 가게 마감 시간

        boolean isOpen;

        if (store.getDeletedAt() != null) {
            throw new CustomException(ErrorCode.STORE_NOT_FOUND);
        }

        if (openTime.isBefore(closeTime)) {
            isOpen = !now.isBefore(openTime) && !now.isAfter(closeTime);
        } else {
            isOpen = !now.isBefore(openTime) || !now.isAfter(closeTime);
        }

        if (!isOpen) {
            throw new RuntimeException("지금은 주문하실 수 없습니다. (운영시간: " + openTime + " ~ " + closeTime + ")");
        }


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
            if(menu.getMenuStatus() == MenuStatus.DELETED) {
                throw new RuntimeException("주문하실수 없는 메뉴가 있습니다.");
            }
            return new OrderMenu(menu, menuDto.getQuantity());
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

    public OrderResponseDto findOrderById (Long storeId, Long orderId){

        Store store = storeRepository.findStoreByIdOrElseThrow(storeId);

        Order findOrderById = orderRepository.findOrderById(orderId);

        return new OrderResponseDto(findOrderById);
    }

    public List<OrderResponseDto> findAll (){

        return orderRepository.findAll()
                .stream()
                .map(OrderResponseDto::findAll)
                .toList();
    }


    @Transactional
    public OrderStatusResponseDto changedStatus (Long storeId, Long orderId,OrderStatus oldOrderStatus, OrderStatus newOrderStatus){

        Store store = storeRepository.findStoreByIdOrElseThrow(storeId);

        Order findOrderById = orderRepository.findOrderByIdOrElseThrow(orderId);
        if(!findOrderById.getOrderStatus().equals(oldOrderStatus)){
            throw new RuntimeException("상태값이 서로 다릅니다. ");
        }
        findOrderById.updateStatus(newOrderStatus);

        return new OrderStatusResponseDto(findOrderById);
    }

}