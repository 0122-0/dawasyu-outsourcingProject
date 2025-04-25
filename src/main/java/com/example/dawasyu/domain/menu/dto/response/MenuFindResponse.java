package com.example.dawasyu.domain.menu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class MenuFindResponse {

    private Long id;

    private String name;

    private Long price;

    private String description;

}
