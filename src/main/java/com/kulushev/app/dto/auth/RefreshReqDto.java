package com.kulushev.app.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshReqDto(
        @NotBlank(message = "Login must not be blank")
        String login,
        @NotBlank(message = "Refresh token must not be blank")
        String refreshToken
) {

}
