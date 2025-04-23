package com.example.dawasyu.domain.user.entity;

import com.example.dawasyu.common.baseEntity.BaseEntity;
import com.example.dawasyu.domain.order.entity.Order;
import com.example.dawasyu.domain.review.entity.Review;
import com.example.dawasyu.domain.store.entity.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하이어야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*\\W)$")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "이름 필수 입력 값입니다.")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String number;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private String roadAddress;

    @Column(nullable = false)
    private String detailAddress;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    // 한 명의 유저는 여러 가게(사장님 입장)
    @OneToMany(mappedBy = "user")
    private List<Store> stores = new ArrayList<>();

    // 한 명의 유저는 여러 리뷰 작성
    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    // 한 명의 유저는 여러 주문 가능
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

}
