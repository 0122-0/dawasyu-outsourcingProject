package com.example.dawasyu.domain.order.entity;

import com.example.dawasyu.common.baseEntity.BaseEntity;
import com.example.dawasyu.domain.menu.entity.Menu;
import com.example.dawasyu.domain.store.entity.Store;
import com.example.dawasyu.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.swing.border.Border;

@Getter
@Entity
@Table(name = "order")
@NoArgsConstructor
public class Order extends BaseEntity {

    //임의의 주문ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //임의의 난수로 구성된 주문 번호 (YYYYMMDDxxxxxx)
    @NotBlank
    @Column (nullable = false)
    private String orderNumber;

    @NotBlank
    @Column
    private int totalPrice;

    //eunm을 사용한 주문상태 확인시켜주기
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToMany
    @JoinColumn(name = "orderMenu_id")
    private OrderMenu menu;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
