package com.example.dawasyu.domain.store.service;

import com.example.dawasyu.common.error.CustomException;
import com.example.dawasyu.common.error.ErrorCode;
import com.example.dawasyu.domain.category.entity.Category;
import com.example.dawasyu.domain.category.service.CategoryService;
import com.example.dawasyu.domain.store.dto.request.StoreCreateRequestDTO;
import com.example.dawasyu.domain.store.dto.response.StoreResponseDTO;
import com.example.dawasyu.domain.store.entity.Store;
import com.example.dawasyu.domain.storecategory.entity.StoreCategory;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.repository.StoreRepository;
import com.example.dawasyu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    @Transactional
    public StoreResponseDTO createStore(StoreCreateRequestDTO reqDTO, User user) {

        User owner = userRepository.findUserByOnwerIdOrElseThrow(user.getId());
        int storeCount = storeRepository.countByOwner(user);

        if(storeCount >= 3) {
            throw new CustomException(ErrorCode.STORE_LIMIT_MAX);
        }

        Store store = reqDTO.toEntity(owner);
        storeRepository.save(store);

        for (Long categoryId : reqDTO.getCategories()) {

            // 카테고리에 해당하는 ID 값을 찾는다.
            Category category = categoryService.getCategoryById(categoryId);

            // SAVE된 상점객체와 요청받은 카테고리객체를 매개변수로 전달 Ex) Store_id, category_id
            StoreCategory storeCategory = StoreCategory.toEntity(store, category);

            // Store 객체안에 List 형식인 storeCategories 변수에 category 객체를 하나 하나 추가한다.
            store.getStoreCategories().add(storeCategory);
        }

        // List형식으로 add된 category객체들을 연관관계를 통해 중간 DB에 등록하고, 해당 객체들을 리턴
        return StoreResponseDTO.toSave(storeRepository.save(store));
    }

    @Transactional(readOnly = true)
    public StoreResponseDTO getStoreById(Long storeId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        if (store.getDeletedAt() != null) {
            throw new CustomException(ErrorCode.ALREADY_CLOSED_STORE);
        }

        return StoreResponseDTO.toDto(store);
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDTO> findOpenStores() {

        LocalTime queryTime = LocalTime.now();

        List<Store> stores = storeRepository.findStoresOpenAt(queryTime);

        List<StoreResponseDTO> result = new ArrayList<>();
        for (Store store : stores) {
            result.add(StoreResponseDTO.toDto(store));
        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDTO> findStoresByName(String name) {

        List<Store> stores = storeRepository.findByNameContainingAndDeletedAtIsNull(name);

        if (stores.isEmpty()) {
            throw new CustomException(ErrorCode.STORE_NOT_FOUND);
        }

        List<StoreResponseDTO> result = new ArrayList<>();
        for (Store store : stores) {
            result.add(StoreResponseDTO.toDto(store));
        }

        return result;
    }

    //가게 폐업
    @Transactional
    public void closeStore(Long storeId, User user) {

        Store store = storeRepository.findStoreByIdOrElseThrow(storeId);

        if (store.getDeletedAt() != null) {
            throw new CustomException(ErrorCode.ALREADY_CLOSED_STORE);
        }

        if (!store.getOwner().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.NO_AUTHORIZATION);
        }

        store.closeStore();
    }
}