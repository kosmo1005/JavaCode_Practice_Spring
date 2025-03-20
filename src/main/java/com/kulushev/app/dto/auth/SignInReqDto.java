package com.kulushev.app.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record SignInReqDto(
        @NotBlank(message = "Login must not be blank")
        String login,
        @NotBlank(message = "Password must not be blank")
        String password
) {

}
