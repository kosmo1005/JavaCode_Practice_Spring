package com.kulushev.app.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record GoodRespDto(
        UUID id,
        UUID orderId,
        String name,
        BigDecimal price
) {
}