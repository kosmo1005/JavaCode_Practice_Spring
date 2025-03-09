package com.kulushev.app.controller;

import com.kulushev.app.dto.OrderReqDto;
import com.kulushev.app.dto.OrderRespDto;
import com.kulushev.app.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Page<OrderRespDto> getAll(@RequestParam(defaultValue = "0") @Min(0) int page,
                                     @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        return orderService.getAll(page, size);
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

