package com.kulushev.app.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.kulushev.app.enums.Role;
import com.kulushev.app.views.Views;

import java.util.List;

public record UserRespDto(
        @JsonView({Views.FullInfo.class, Views.AuthView.class}) String id,
        @JsonView({Views.ShortInfo.class, Views.AuthView.class}) String login,
        @JsonView({Views.ShortInfo.class, Views.AuthView.class}) String email,
        @JsonView({Views.ShortInfo.class, Views.AuthView.class}) Role role,
        @JsonView(Views.ShortInfo.class) String name,
        @JsonView(Views.FullInfo.class) List<OrderRespDto> orders
        ) {
}