package com.example.dawasyu.domain.menu.dto.request;


import com.example.dawasyu.domain.menu.entity.MenuStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateMenuRequest {

    @Size(max=10, message = "10글자를 초과하지 마십시오.")
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Long price;

    @NotNull
    private MenuStatus menuStatus;
}






