package com.kulushev.app.dto;

import com.kulushev.app.entity.GoodEntity;
import com.kulushev.app.entity.OrderEntity;

import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link OrderEntity}
 */
public record OrderReqDto(
        UUID userId,
        List<GoodEntity> goods
) {
}