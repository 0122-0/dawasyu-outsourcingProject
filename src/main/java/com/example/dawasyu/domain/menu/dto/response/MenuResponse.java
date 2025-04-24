package com.example.dawasyu.domain.menu.dto.response;

import com.example.dawasyu.domain.menu.entity.Menu;
import com.example.dawasyu.domain.menu.entity.MenuStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuResponse {

    private Long id;

    private String name;

    private Long price;

    private MenuStatus menuStatus;

    public static MenuResponse from(Menu menu){
        return new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getMenuStatus()

        );
    }
}
