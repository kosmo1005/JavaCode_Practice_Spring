package com.kulushev.app.dto;

import java.util.List;
import java.util.UUID;

public record OrderReqDto(
        UUID userId,
        List<GoodReqDto> goods
) {
}