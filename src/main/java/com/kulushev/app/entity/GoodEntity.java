package com.kulushev.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodEntity {

    private UUID id;
    private String name;
    private BigDecimal price;
    private UUID orderId;
}