package com.kulushev.app.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.kulushev.app.views.Views;

import java.util.List;
import java.util.UUID;

public record UserRespDto(
        @JsonView(Views.FullInfo.class) UUID id,
        @JsonView(Views.ShortInfo.class) String name,
        @JsonView(Views.ShortInfo.class) String email,
        @JsonView(Views.FullInfo.class) List<OrderRespDto> orders
        ) {
}