package com.example.dawasyu.domain.category.dto.response;

import com.example.dawasyu.domain.category.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponseDTO {
    private Long id;
    private String name;

    public static CategoryResponseDTO toDto(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
