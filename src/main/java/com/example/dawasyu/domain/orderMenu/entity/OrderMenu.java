package com.example.dawasyu.domain.orderMenu.entity;

import com.example.dawasyu.domain.menu.entity.Menu;
import com.example.dawasyu.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "order_menu",
uniqueConstraints = @UniqueConstraint(columnNames = {"order_id","menu_id"}))
public class OrderMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    private int quantity;

    public void matchOrder(Order order) {
        this.order = order;
    }

    public OrderMenu(Menu menu,int quantity) {
        this.menu = menu;
//        this.order = order;
        this.quantity = quantity;
    }
}
