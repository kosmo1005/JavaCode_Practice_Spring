package com.kulushev.app.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.kulushev.app.enums.Role;
import com.kulushev.app.views.Views;

import java.util.List;
import java.util.UUID;

public record UserRespDto(
        @JsonView({Views.FullInfo.class, Views.AuthView.class}) UUID id,
        @JsonView({Views.ShortInfo.class, Views.AuthView.class}) String login,
        @JsonView({Views.ShortInfo.class, Views.AuthView.class}) String email,
        @JsonView({Views.ShortInfo.class, Views.AuthView.class}) Role role,
        @JsonView( Views.AuthView.class) int countOfFailedAuth,
        @JsonView(Views.AuthView.class) boolean accountLocked,
        @JsonView(Views.ShortInfo.class) String firstName,
        @JsonView(Views.FullInfo.class) String lastName,
        @JsonView(Views.FullInfo.class) List<OrderRespDto> orders
        ) {
}