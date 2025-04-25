package com.example.dawasyu.domain.category.dto.request;

import com.example.dawasyu.domain.category.entity.Category;
import com.example.dawasyu.domain.store.entity.Store;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryCreateRequestDTO {

    @NotBlank
    private String name;

    public Category toEntity() {
        return Category.builder()
                .name(this.name)
                .build();
    }
}