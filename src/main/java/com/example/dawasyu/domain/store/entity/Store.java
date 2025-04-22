package com.example.dawasyu.domain.store.entity;

import com.example.dawasyu.common.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@Entity
@Table(name = "store")
@NoArgsConstructor
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String number;

    private String bizNo;

    private String address1;

    private String address2;

    private int minPrice;

    private LocalTime openTime;

    private LocalTime closeTime;


}
