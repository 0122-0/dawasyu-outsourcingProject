package com.example.dawasyu.common.reponseMessege;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Lombok 라이브러리를 사용한다. Getter 메서드를 자동으로 생성한다. 이후 생성자나 빌더를 통해 객체에 주입된다.
@Builder
public class ResponseMessage<T> {
    private int statusCode;
    private String message;
    private T data;
}
