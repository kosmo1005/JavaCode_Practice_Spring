package com.kulushev.app.dto;

import com.kulushev.app.validators.GlobalValidator;
import java.math.BigDecimal;

@GlobalValidator
public record GoodReqDto(
        String name,
        BigDecimal price
) {
}