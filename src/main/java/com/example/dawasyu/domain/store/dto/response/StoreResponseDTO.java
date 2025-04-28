package com.example.dawasyu.domain.store.dto.response;

import com.example.dawasyu.domain.category.dto.response.CategoryResponseDTO;
import com.example.dawasyu.domain.menu.dto.response.MenuFindResponse;
import com.example.dawasyu.domain.menu.entity.MenuStatus;
import com.example.dawasyu.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class StoreResponseDTO {
    private Long id;
    private String name;
    private String number;
    private String bizNo;
    private String roadAddress;
    private String detailAddress;
    private int minPrice;
    private LocalTime openTime;
    private LocalTime closeTime;
    private List<CategoryResponseDTO> categories;
    private List<MenuFindResponse> menus;

    public static StoreResponseDTO toSave(Store store) {
        return StoreResponseDTO.builder()
                .id(store.getId())
                .name(store.getName())
                .number(store.getNumber())
                .bizNo(store.getBizNo())
                .roadAddress(store.getRoadAddress())
                .detailAddress(store.getDetailAddress())
                .minPrice(store.getMinPrice())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .categories(
                        store.getStoreCategories().stream()
                                .map(storeCategory -> CategoryResponseDTO.toDto(storeCategory.getCategory()))
                                .toList()
                )
                .build();
    }

    public static StoreResponseDTO toDto(Store store) {
        return StoreResponseDTO.builder()
                .id(store.getId())
                .name(store.getName())
                .number(store.getNumber())
                .bizNo(store.getBizNo())
                .roadAddress(store.getRoadAddress())
                .detailAddress(store.getDetailAddress())
                .minPrice(store.getMinPrice())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .categories(
                        store.getStoreCategories().stream()
                                .map(storeCategory -> CategoryResponseDTO.toDto(storeCategory.getCategory()))
                                .toList()
                )
                .menus(
                        store.getMenuList().stream()
                                .filter(menu -> menu.getMenuStatus() != MenuStatus.DELETED)
                                .map(MenuFindResponse::from)
                                .toList()
                )
                .build();
    }
}
