package com.example.dawasyu.domain.category.service;

import com.example.dawasyu.domain.category.dto.request.CategoryCreateRequestDTO;
import com.example.dawasyu.domain.category.dto.response.CategoryResponseDTO;
import com.example.dawasyu.domain.category.entity.Category;
import com.example.dawasyu.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponseDTO createCategory(CategoryCreateRequestDTO reqDTO) {

        Category categoryToSave = reqDTO.toEntity();

        return CategoryResponseDTO.toDto(categoryRepository.save(categoryToSave));
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다. id=" + id));
    }
}
