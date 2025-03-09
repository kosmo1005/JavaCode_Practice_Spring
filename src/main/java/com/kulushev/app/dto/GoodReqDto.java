package com.kulushev.app.dto;

import com.kulushev.app.validators.GlobalValidator;

import java.math.BigDecimal;

/**
 * DTO for {@link com.kulushev.app.entity.GoodEntity}
 */
@GlobalValidator
public record GoodReqDto(
        String name,
        BigDecimal price
) {
}