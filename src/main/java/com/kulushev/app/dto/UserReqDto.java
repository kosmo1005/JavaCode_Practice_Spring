package com.kulushev.app.dto;

import com.kulushev.app.validators.GlobalValidator;
import jakarta.validation.constraints.NotBlank;


@GlobalValidator
public record UserReqDto(
        @NotBlank(message = "First name must not be blank")
        String firstName,

        @NotBlank(message = "Last name must not be blank")
        String lastName,

        @NotBlank(message = "Email must not be blank")
        String email,

        @NotBlank(message = "Login must not be blank")
        String login,

        String password,
        String token,
        String refreshToken
        ) {
}