package com.kulushev.app.dto;

import com.kulushev.app.entity.OrderEntity;
import com.kulushev.app.enums.OrderStatus;
import com.kulushev.app.validators.GlobalValidator;

import java.util.List;

/**
 * DTO for {@link OrderEntity}
 */
@GlobalValidator
public record OrderReqDto(
        String userId,
        List<GoodReqDto> goods,
        OrderStatus status
) {
}