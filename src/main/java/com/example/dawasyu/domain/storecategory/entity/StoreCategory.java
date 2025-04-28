package com.example.dawasyu.domain.storecategory.entity;

import com.example.dawasyu.domain.category.entity.Category;
import com.example.dawasyu.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "store_category",
        uniqueConstraints = @UniqueConstraint(columnNames = {"store_id", "category_id"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class StoreCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public static StoreCategory toEntity(Store store, Category category) {
        return StoreCategory.builder()
                .store(store)
                .category(category)
                .build();
    }
}
