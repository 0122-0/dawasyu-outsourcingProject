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

    public ResponseStoreDTO createStore(RequestStoreDTO requestStoreDTO, Long ownerId) {

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다"));

        Store storeToSave = requestStoreDTO.toEntity(owner);

        Store saved = storeRepository.save(storeToSave);

        return ResponseStoreDTO.toDto(saved);
    }
}
