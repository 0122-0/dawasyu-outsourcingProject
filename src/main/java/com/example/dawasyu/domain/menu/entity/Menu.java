package com.example.dawasyu.domain.menu.entity;

import com.example.dawasyu.common.baseEntity.BaseEntity;
import com.example.dawasyu.domain.orderMenu.entity.OrderMenu;
import com.example.dawasyu.domain.store.entity.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "menu")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private Long price;


    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuStatus menuStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();



    //메뉴 생성 부분
    public Menu(String name, String description, Long price, MenuStatus menuStatus, Store store) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.menuStatus = menuStatus;
        this.store = store;

    }

    public void update(String name, String description, Long price, MenuStatus menuStatus) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.menuStatus = menuStatus;
    }

    public void setDeleted() {
        this.menuStatus = MenuStatus.DELETED;
    }


}