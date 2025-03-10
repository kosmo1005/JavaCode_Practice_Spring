package com.kulushev.app.dto;

import java.math.BigDecimal;

public record GoodRespDto(
        Long id,
        Long orderId,
        String name,
        BigDecimal price
) {
}