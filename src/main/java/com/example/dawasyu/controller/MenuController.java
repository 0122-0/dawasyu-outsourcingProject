package com.example.dawasyu.controller;

import com.example.dawasyu.domain.menu.dto.request.CreateMenuRequest;
import com.example.dawasyu.domain.menu.dto.request.UpdateMenuRequest;
import com.example.dawasyu.domain.menu.dto.response.MenuFindResponse;
import com.example.dawasyu.domain.menu.dto.response.MenuResponse;
import com.example.dawasyu.domain.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores/{storeId}/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // POST 메뉴 생성
    @PostMapping
    public ResponseEntity<Void> create(
            @PathVariable Long storeId,
            @RequestBody @Valid CreateMenuRequest request
    ){
        menuService.create(storeId,request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // PATCH 메뉴 수정
    @PatchMapping("/{menuId}")
    public ResponseEntity<String> updateById(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody @Valid UpdateMenuRequest request
    ){
        menuService.updateMenu(storeId,menuId,request);
        return ResponseEntity.ok("메뉴 수정 완료");

    }
    // GET 메뉴 목록조회
    @GetMapping
    public ResponseEntity<List<MenuResponse>> findAll(@PathVariable Long storeId){
        return ResponseEntity.ok(menuService.findAll(storeId));
    }

    // DELETE 메뉴 삭제
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId
    ){

        menuService.deleteMenu(storeId, menuId);

        return ResponseEntity.noContent().build();
    }

    // GET 메뉴 단일조회 이름, 설명, 값
    @GetMapping("/{menuId}")
    public ResponseEntity<MenuFindResponse> findById(
            @PathVariable Long storeId,
            @PathVariable Long menuId
    ){
        return ResponseEntity.ok(menuService.findById(storeId,menuId));
    }
}