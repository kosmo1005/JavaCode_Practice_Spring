package com.kulushev.app.controller;

import com.kulushev.app.dto.OrderReqDto;
import com.kulushev.app.dto.OrderRespDto;
import com.kulushev.app.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderRespDto getById(@PathVariable UUID id) {
        return orderService.getById(id);
    }

    @GetMapping
    public List<OrderRespDto> getAll() {
        return orderService.getAll();
    }

    @PostMapping
    public OrderRespDto create(@Valid @RequestBody OrderReqDto reqDto) {
        return orderService.createOrder(reqDto);
    }

    @PutMapping("/{id}")
    public OrderRespDto update(@PathVariable UUID id, @RequestBody OrderReqDto reqDto) {
        return orderService.updateOrder(id, reqDto);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        orderService.deleteOrderById(id);
    }
}

