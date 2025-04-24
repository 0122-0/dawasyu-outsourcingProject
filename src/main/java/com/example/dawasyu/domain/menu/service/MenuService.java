package com.example.dawasyu.domain.menu.service;

import com.example.dawasyu.domain.menu.dto.request.CreateMenuRequest;

import com.example.dawasyu.domain.menu.dto.request.UpdateMenuRequest;
import com.example.dawasyu.domain.menu.entity.Menu;
import com.example.dawasyu.domain.store.entity.Store;
import com.example.dawasyu.repository.MenuRepository;

import com.example.dawasyu.repository.StoreRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public void create(Long storeId,CreateMenuRequest request) {
        Store findStore = storeRepository.findById(storeId)
                .orElseThrow(()->new RuntimeException("가게를 찾을 수 없습니다."));
        Menu menu = new Menu(
                request.getMenuName(),
                request.getDescription(),
                request.getPrice(),
                request.getMenuStatus(),
                findStore
        );
        menuRepository.save(menu);


    }


    public void updateById(Long storeId,UpdateMenuRequest request) {
        Store findStore = storeRepository.findById(storeId)
                .orElseThrow(()->new RuntimeException("가게를 찾을 수 없습니다."));
        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(()-> new RuntimeException("메뉴를 찾을 수 없습니다"));
    }
}