package com.example.dawasyu.controller;


import com.example.dawasyu.common.reponseMessege.ResponseMessage;
import com.example.dawasyu.domain.category.dto.request.CategoryCreateRequestDTO;
import com.example.dawasyu.domain.category.dto.response.CategoryResponseDTO;
import com.example.dawasyu.domain.category.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseMessage<CategoryResponseDTO>> createCategory(
            @Valid @RequestBody CategoryCreateRequestDTO reqDTO){

        CategoryResponseDTO resDTO = categoryService.createCategory(reqDTO);

        ResponseMessage<CategoryResponseDTO> responseMessage = ResponseMessage.<CategoryResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("카테고리가 생성되었습니다.")
                .data(resDTO)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @GetMapping
    public ResponseEntity<ResponseMessage<List<CategoryResponseDTO>>> getAllCategories() {

        List<CategoryResponseDTO> resDTOList = categoryService.getAllCategories();

        ResponseMessage<List<CategoryResponseDTO>> responseMessage = ResponseMessage.<List<CategoryResponseDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("전체 카테고리 목록 조회가 완료되었습니다.")
                .data(resDTOList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
