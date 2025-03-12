package com.kulushev.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserEntity {

    private UUID id;
    private String name;
    private String email;
    private List<OrderEntity> orders;
}