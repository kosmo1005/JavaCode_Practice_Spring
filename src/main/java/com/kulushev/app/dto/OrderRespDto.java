package com.kulushev.app.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.kulushev.app.enums.OrderStatus;
import com.kulushev.app.views.Views;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@JsonView(Views.FullInfo.class)
public record OrderRespDto(
        Long id,
        UUID userId,
        OrderStatus status,
        BigDecimal totalPrice,
        List<GoodRespDto> goods
) {
}