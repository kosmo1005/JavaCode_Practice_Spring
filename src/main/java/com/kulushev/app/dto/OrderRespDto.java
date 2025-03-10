package com.kulushev.app.dto;

import com.kulushev.app.enums.OrderStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderRespDto(
        Long id,
        UUID userId,
        OrderStatus status,
        BigDecimal totalPrice,
        List<GoodRespDto> goods
) {
}