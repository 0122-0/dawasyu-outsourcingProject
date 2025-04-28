package com.example.dawasyu.controller;

import com.example.dawasyu.common.reponseMessege.ResponseMessage;
import com.example.dawasyu.domain.user.entity.User;

import com.example.dawasyu.common.annotation.LoginUser;
import com.example.dawasyu.domain.store.dto.request.StoreCreateRequestDTO;
import com.example.dawasyu.domain.store.dto.response.StoreResponseDTO;
import com.example.dawasyu.domain.store.service.StoreService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    // 가게등록
    @PostMapping
    public ResponseEntity<ResponseMessage<StoreResponseDTO>> createStore(@Valid @RequestBody StoreCreateRequestDTO reqDTO,
                                                                         @LoginUser User user){

        StoreResponseDTO resDTO = storeService.createStore(reqDTO, user);

        ResponseMessage<StoreResponseDTO> responseMessage = ResponseMessage.<StoreResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("가게가 등록되었습니다.")
                .data(resDTO)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    // 가게조회
    @GetMapping("/{storeId}")
    public ResponseEntity<ResponseMessage<StoreResponseDTO>> getStore(@PathVariable Long storeId) {

        StoreResponseDTO resDTO = storeService.getStoreById(storeId);

        ResponseMessage<StoreResponseDTO> responseMessage = ResponseMessage.<StoreResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("가게 조회가 완료되었습니다.")
                .data(resDTO)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // 오픈된 가게조회
    @GetMapping("/open")
    public ResponseEntity<ResponseMessage<List<StoreResponseDTO>>> getOpenStores() {

        List<StoreResponseDTO> resDTOList = storeService.findOpenStores();

        ResponseMessage<List<StoreResponseDTO>> responseMessage = ResponseMessage.<List<StoreResponseDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("현재 열려 있는 가게 목록 조회가 완료되었습니다.")
                .data(resDTOList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // 이름으로 가게 조회
    @GetMapping("/search")
    public ResponseEntity<ResponseMessage<List<StoreResponseDTO>>> getStoresByName(@RequestParam String name) {

        List<StoreResponseDTO> resDTOList = storeService.findStoresByName(name);

        ResponseMessage<List<StoreResponseDTO>> responseMessage = ResponseMessage.<List<StoreResponseDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("가게 이름으로 조회가 완료되었습니다.")
                .data(resDTOList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // 가게 폐업하기
    @DeleteMapping("/{storeId}")
    public ResponseEntity<ResponseMessage<Void>> closeStore(@PathVariable Long storeId, @LoginUser User user) {

        storeService.closeStore(storeId, user);

        ResponseMessage<Void> responseMessage = ResponseMessage.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("가게가 폐업 처리되었습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // 오픈된 가게 ID 조회 시 메뉴도 조회


}