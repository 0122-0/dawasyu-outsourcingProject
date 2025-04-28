package com.example.dawasyu.domain.menu.dto.request;


import com.example.dawasyu.domain.menu.entity.MenuStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateMenuRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Long price;

    @NotNull
    private MenuStatus menuStatus;
}






