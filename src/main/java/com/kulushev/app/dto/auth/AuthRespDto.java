package com.kulushev.app.dto.auth;


import com.fasterxml.jackson.annotation.JsonView;
import com.kulushev.app.dto.UserRespDto;
import com.kulushev.app.views.Views;

public record AuthRespDto(
        @JsonView(Views.AuthView.class) String token,
        @JsonView(Views.AuthView.class) String refreshToken,
        @JsonView(Views.AuthView.class) UserRespDto user
) {
}
