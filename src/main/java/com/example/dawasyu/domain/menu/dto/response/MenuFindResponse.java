package com.example.dawasyu.domain.menu.dto.response;

import com.example.dawasyu.domain.menu.entity.Menu;
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

    public static MenuFindResponse from(Menu menu){
        return new MenuFindResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getDescription()
        );
    }
}
