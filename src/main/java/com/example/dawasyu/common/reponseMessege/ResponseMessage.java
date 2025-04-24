package com.example.dawasyu.common.reponseMessege;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
public class ResponseMessage<T> {
    private int statusCode;
    private String message;
    private T data;
}
