package com.example.dawasyu.domain.store.service;

import com.example.dawasyu.domain.store.dto.request.RequestStoreDTO;
import com.example.dawasyu.domain.store.dto.response.ResponseStoreDTO;
import com.example.dawasyu.domain.store.entity.Store;
import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.repository.StoreRepository;
import com.example.dawasyu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public ResponseStoreDTO createStore(RequestStoreDTO requestStoreDTO, User loginUser) {

        User owner = userRepository.findUserByOnwerIdOrElseThrow(loginUser.getId());

        int storeCount = storeRepository.countByOwner(loginUser);

        if(storeCount >= 3) {
            throw new RuntimeException("가게는 3개까지만 만들 수 있습니다.");
        }

        Store storeToSave = requestStoreDTO.toEntity(owner);

        Store saved = storeRepository.save(storeToSave);

        return ResponseStoreDTO.toDto(saved);
    }
}
