package com.example.dawasyu.domain.menu.service;

import com.example.dawasyu.common.error.CustomException;
import com.example.dawasyu.common.error.ErrorCode;
import com.example.dawasyu.domain.menu.dto.request.CreateMenuRequest;

import com.example.dawasyu.domain.menu.dto.request.UpdateMenuRequest;
import com.example.dawasyu.domain.menu.dto.response.MenuFindResponse;
import com.example.dawasyu.domain.menu.dto.response.MenuResponse;
import com.example.dawasyu.domain.menu.entity.Menu;
import com.example.dawasyu.domain.menu.entity.MenuStatus;
import com.example.dawasyu.domain.store.entity.Store;
import com.example.dawasyu.repository.MenuRepository;

import com.example.dawasyu.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public void create(Long storeId,CreateMenuRequest request) {
        Store findStore = StoreNotFound(storeId);
        Menu menu = new Menu(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getMenuStatus(),
                findStore
        );
        menuRepository.save(menu);
    }

    @Transactional
    public void updateMenu(Long storeId, Long menuId,UpdateMenuRequest request) {
        Store store = StoreNotFound(storeId);
        Menu menu = MenuNotFound(menuId);

            // 다른 상태로 변경할 때는 기존의 update 메서드 사용
            menu.update(request.getName(), request.getDescription(), request.getPrice(), request.getMenuStatus());

    }

    public List<MenuResponse> findAll(Long storeId) {
        Store store = StoreNotFound(storeId);

        return menuRepository.findAll()
                .stream()
                .filter(menu -> menu.getMenuStatus() == MenuStatus.ACTIVE)
                .map(MenuResponse::from)
                .toList();
    }

    @Transactional
    public void deleteMenu(Long storeId, Long menuId) {
        Store store = StoreNotFound(storeId);
        Menu menu = MenuNotFound(menuId);
        menu.setDeleted();
    }

    // 메뉴 딜리티드일때 주문불가능하게 메뉴 목록 조회할때 딜리티드이면 못보게
    public MenuFindResponse findById(Long storeId, Long menuId) {
        Menu menu = MenuNotFound(menuId);
        Store store = StoreNotFound(storeId);

        if(menu.getMenuStatus() == MenuStatus.DELETED){
            throw new CustomException(ErrorCode.MENU_ALREADY_DELETED);
        }

        return MenuFindResponse.from(menu);
    }

    // 메뉴 존재 하지않을 경우 예외처리
    private Menu MenuNotFound(Long menuId){
        return menuRepository.findById(menuId)
                .orElseThrow(()->new CustomException(ErrorCode.MENU_NOT_FOUND));
    }

    // 스토어 아이디 존재하지 않을 경우 예외처리
    private Store StoreNotFound(Long storeId){
        return storeRepository.findById(storeId)
                .orElseThrow(()->new CustomException(ErrorCode.STORE_NOT_FOUND));
    }
}