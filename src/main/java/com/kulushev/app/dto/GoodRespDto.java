package com.kulushev.app.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.kulushev.app.views.Views;

import java.math.BigDecimal;

/**
 * DTO for {@link com.kulushev.app.entity.GoodEntity}
 */
@JsonView(Views.FullInfo.class)
public record GoodRespDto(
        Long id,
        Long orderId,
        String name,
        BigDecimal price
) {
}