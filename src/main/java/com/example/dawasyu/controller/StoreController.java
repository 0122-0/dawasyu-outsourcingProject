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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    // 가게등록
    /**
     [RequestBody]

     {
     "name": "호식이두마리",
     "number": "010-1234-5678",
     "bizNo": "123-45-67890",
     "roadAddress": "서울시 성동구 왕십리로 222",
     "detailAddress": "상가 101호",
     "minPrice": 10000,
     "openTime": "10:00",
     "closeTime": "22:00",
     "categories": [ 1, 2 ]
     }
     **/
    @PostMapping
    public ResponseEntity<ResponseMessage<StoreResponseDTO>> createStore(@Valid @RequestBody StoreCreateRequestDTO reqDTO,
                                                                         @LoginUser User user){

        StoreResponseDTO resDTO = storeService.createStore(reqDTO, user);

        ResponseMessage<StoreResponseDTO> responseMessage = ResponseMessage.<StoreResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("가게가 등록되었습니다.")
                .data(resDTO)
                .build();
        /**
         [responseMessage]

         {
         "statusCode": 201,
         "message": "가게가 등록되었습니다.",
         "data": {
         "id": 6,
         "name": "호식이두마리",
         "number": "010-1234-5678",
         "bizNo": "123-45-67890",
         "roadAddress": "서울시 성동구 왕십리로 222",
         "detailAddress": "상가 101호",
         "minPrice": 10000,
         "openTime": "10:00:00",
         "closeTime": "22:00:00",
         "categories": [
         {
         "id": 1,
         "name": "야식"
         },
         {
         "id": 2,
         "name": "치킨"
         }
         ]
         }
         }
         **/

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }
}
