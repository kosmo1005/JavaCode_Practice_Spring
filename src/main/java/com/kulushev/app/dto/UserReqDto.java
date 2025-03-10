package com.kulushev.app.dto;

import com.kulushev.app.validators.GlobalValidator;

@GlobalValidator
public record UserReqDto(
        String name,
        String email
) {
}