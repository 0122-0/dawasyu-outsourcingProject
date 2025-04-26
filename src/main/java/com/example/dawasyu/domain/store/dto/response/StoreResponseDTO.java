package com.example.dawasyu.domain.store.dto.response;

import com.example.dawasyu.domain.category.dto.response.CategoryResponseDTO;
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
                        // 1. Store 안에있는 List<StoreCategory>를 가져옴
                        // 2. 이 리스트를 Strea API로 변환 ( 리스트를 순차적으로 돌리면서 처리 할 수 있게 해줌)
                        // 3. map이 핵심동작 각각 StoreCategory 객체에서 Category 엔티티를 꺼내서 그걸 toDto 로 반환
                        store.getStoreCategories().stream()
                                .map(storeCategory -> CategoryResponseDTO.toDto(storeCategory.getCategory()))
                                .toList()
                )
                .build();
    }
}
