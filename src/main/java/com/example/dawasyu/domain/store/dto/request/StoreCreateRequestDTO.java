package com.example.dawasyu.domain.store.dto.request;

import com.example.dawasyu.domain.store.entity.Store;
import com.example.dawasyu.domain.user.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class StoreCreateRequestDTO {

    @NotBlank(message = "Store name must not be blank.")
    private String name;

    @NotBlank(message = "Phone number must not be blank.")
    private String number;

    @NotBlank(message = "Business number must not be blank.")
    private String bizNo;

    @NotBlank(message = "Road address must not be blank.")
    private String roadAddress;

    private String detailAddress;

    @Min(value = 0, message = "Minimum order price must be zero or positive.")
    private int minPrice;

    @NotBlank(message = "Open time must not be blank. (ex: 10:00)")
    private String openTime;

    @NotBlank(message = "Close time must not be blank. (ex: 22:00)")
    private String closeTime;

    @NotEmpty(message = "At least one category must be provided.")
    private List<Long> categories;

    public Store toEntity(User owner) {
        return Store.builder()
                .name(this.name)
                .number(this.number)
                .bizNo(this.bizNo)
                .roadAddress(this.roadAddress)
                .detailAddress(this.detailAddress)
                .minPrice(this.minPrice)
                .openTime(LocalTime.parse(this.openTime))
                .closeTime(LocalTime.parse(this.closeTime))
                .owner(owner)
                .build();
    }
}
