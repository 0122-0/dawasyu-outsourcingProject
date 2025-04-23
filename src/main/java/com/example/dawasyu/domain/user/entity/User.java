package com.example.dawasyu.domain.user.entity;

import com.example.dawasyu.common.baseEntity.BaseEntity;
import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.review.entity.Review;
import com.example.dawasyu.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private String roadAddress;

    @Column(nullable = false)
    private String detailAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    // 한 명의 유저는 여러 가게(사장님 입장)
    @OneToMany(mappedBy = "owner")
    private List<Store> stores = new ArrayList<>();

    // 한 명의 유저는 여러 리뷰 작성
    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    // 한 명의 유저는 여러 주문 가능
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @Setter
    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean deleted = false;  // 삭제 상태

    public User(String email, String password, String name, String number, String nickname, String roadAddress, String detailAddress, String userRole) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.number = number;
        this.nickName = nickname;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.userRole = UserRole.valueOf(userRole);
    }
}
