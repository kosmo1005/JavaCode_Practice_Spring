package com.kulushev.app.dto;

import java.util.List;
import java.util.UUID;

public record UserRespDto(
        UUID id,
        String name,
        String email,
        List<OrderRespDto> orders
        ) {
}