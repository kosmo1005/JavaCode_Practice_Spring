package com.kulushev.app.dto;

import java.math.BigDecimal;

public record GoodReqDto(
        String name,
        BigDecimal price
) {
}