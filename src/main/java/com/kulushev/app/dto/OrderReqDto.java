package com.kulushev.app.dto;

import com.kulushev.app.enums.OrderStatus;
import com.kulushev.app.validators.GlobalValidator;
import java.util.List;

@GlobalValidator
public record OrderReqDto(
        String userId,
        List<GoodReqDto> goods,
        OrderStatus status
) {
}