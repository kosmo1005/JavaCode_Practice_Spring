package com.kulushev.app.dto;

import java.util.List;

public record OrderReqDto(
        String userId,
        List<GoodReqDto> goods
) {
}