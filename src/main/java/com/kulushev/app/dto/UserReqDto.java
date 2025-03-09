package com.kulushev.app.dto;

import com.kulushev.app.entity.UserEntity;
import com.kulushev.app.validators.GlobalValidator;


/**
 * Request DTO for {@link UserEntity}
 */
@GlobalValidator
public record UserReqDto(
        String name,
        String email
) {
}