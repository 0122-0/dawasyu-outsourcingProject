package com.example.dawasyu.domain.user.entity;

import com.example.dawasyu.common.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String email;

    private String password;

    private String name;

    private String number;

    private String nickName;

    private String address1;

    private String address2;

    private UserRole userRole;


}
