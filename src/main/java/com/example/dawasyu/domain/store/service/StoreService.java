package com.example.dawasyu.domain.store.service;

import com.example.dawasyu.domain.category.entity.Category;
import com.example.dawasyu.domain.category.service.CategoryService;
import com.example.dawasyu.domain.store.dto.request.StoreCreateRequestDTO;
import com.example.dawasyu.domain.store.dto.response.StoreResponseDTO;
import com.example.dawasyu.domain.store.entity.Store;
import com.example.dawasyu.domain.storecategory.entity.StoreCategory;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.repository.StoreCategoryRepository;
import com.example.dawasyu.repository.StoreRepository;
import com.example.dawasyu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final StoreCategoryRepository storeCategoryRepository;

    @Transactional
    public StoreResponseDTO createStore(StoreCreateRequestDTO reqDTO, User user) {

        User owner = userRepository.findUserByOnwerIdOrElseThrow(user.getId());
        int storeCount = storeRepository.countByOwner(user);
        if(storeCount >= 3) {
            throw new RuntimeException("가게는 3개까지만 만들 수 있습니다.");
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
        return StoreResponseDTO.toDto(storeRepository.save(store));
    }
}
