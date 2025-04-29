package com.example.dawasyu.domain.store.entity;

import com.example.dawasyu.common.baseEntity.BaseEntity;
import com.example.dawasyu.domain.storecategory.entity.StoreCategory;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.domain.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA는 사용가능하지만, 외부에서 개발자가 직접 new로 못씀.
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String name;

    @Column(nullable = false, length = 20, unique = true)
    private String number;

    @Column(nullable = false, length = 20, unique = true)
    private String bizNo;

    @Column(nullable = false, length = 100)
    private String roadAddress;

    @Column(length = 100)
    private String detailAddress;

    @Column(nullable = false)
    private int minPrice;

    @Column(nullable = false)
    private LocalTime openTime;

    @Column(nullable = false)
    private LocalTime closeTime;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menuList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreCategory> storeCategories = new ArrayList<>();

    public void closeStore() {
        this.deletedAt = LocalDateTime.now();
    }
}
