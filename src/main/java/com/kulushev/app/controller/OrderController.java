package com.kulushev.app.controller;

import com.kulushev.app.dto.OrderReqDto;
import com.kulushev.app.dto.OrderRespDto;
import com.kulushev.app.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderRespDto getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @GetMapping
    public Page<OrderRespDto> getAll(@PageableDefault(size = 10) Pageable pageable) {
        return orderService.getAll(pageable);
    }

    @PostMapping
    public OrderRespDto create(@Valid @RequestBody OrderReqDto reqDto) {
        return orderService.createOrder(reqDto);
    }

    @PutMapping("/{id}")
    public OrderRespDto update(@PathVariable Long id, @RequestBody OrderReqDto reqDto) {
        return orderService.updateOrder(id, reqDto);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.deleteOrderById(id);
    }


}

