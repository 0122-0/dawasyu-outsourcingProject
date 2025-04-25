package com.example.dawasyu.domain.order.entity;

import com.example.dawasyu.common.baseEntity.BaseEntity;
import com.example.dawasyu.domain.orderMenu.entity.OrderMenu;
import com.example.dawasyu.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order extends BaseEntity {

    //임의의 주문ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //임의의 난수로 구성된 주문 번호 (MMddHHmmss)
    @Column (nullable = false)
    private String orderNumber;

    @Column (nullable = false)
    private Long totalPrice;

    //eunm을 사용한 주문상태 확인시켜주기
    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private OrderStatus orderStatus;

    @OneToMany (mappedBy = "order", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order (Long totalPrice,String orderNumber,User user, List<OrderMenu> orderMenus) {
        for(OrderMenu orderMenu : orderMenus){
            orderMenu.matchOrder(this);
        }
        this.orderMenus.addAll(orderMenus);
        this.totalPrice = totalPrice;
        this.user = user;
        this.orderNumber = orderNumber;
        this.orderStatus = OrderStatus.REQUESTED;
    }
}

