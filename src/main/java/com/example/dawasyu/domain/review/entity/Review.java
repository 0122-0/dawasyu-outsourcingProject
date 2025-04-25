package com.example.dawasyu.domain.review.entity;

import com.example.dawasyu.common.baseEntity.BaseEntity;

import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.store.entity.Store;
import com.example.dawasyu.domain.menu.entity.Menu;
import com.example.dawasyu.domain.user.entity.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA는 사용가능하지만, 외부에서 개발자가 직접 new로 못씀.
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public void updateReview(String content, int rating) {
        this.content = content;
        this.rating = rating;
    }
}
