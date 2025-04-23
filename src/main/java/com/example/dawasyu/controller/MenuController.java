package com.example.dawasyu.controller;

import com.example.dawasyu.domain.menu.dto.request.CreateMenuRequest;
import com.example.dawasyu.domain.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{storeId}/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // POST 메뉴 생성
    @PostMapping
    public ResponseEntity<Void> create(@PathVariable Long storeId,
                                       @RequestBody @Valid CreateMenuRequest request
    ){
        menuService.create(storeId,request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}