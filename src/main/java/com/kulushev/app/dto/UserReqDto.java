package com.kulushev.app.dto;

import com.kulushev.app.validators.GlobalValidator;
import jakarta.validation.constraints.NotBlank;


@GlobalValidator
public record UserReqDto(
        @NotBlank(message = "Name must not be blank")
        String name,

        @NotBlank(message = "Email must not be blank")
        String email
) {
}