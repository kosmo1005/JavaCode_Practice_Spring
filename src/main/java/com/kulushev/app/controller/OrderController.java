package com.kulushev.app.controller;

import com.kulushev.app.dto.OrderReqDto;
import com.kulushev.app.dto.OrderRespDto;
import com.kulushev.app.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/app/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    public String create(@ModelAttribute OrderReqDto reqDto, Model model) {
        try {
            OrderRespDto createdOrder = orderService.createOrder(reqDto);
            model.addAttribute("order", createdOrder);
            return "createdOrder";
        } catch (Exception e) {
            model.addAttribute("statusCode", 500);
            model.addAttribute("errorMessage", "Ошибка создания заказа: " + e.getMessage());
            return "errorPage";
        }
    }

    @GetMapping("/createOrderForm")
    public String createOrderForm(@RequestParam("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "createOrder";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public void delete(@PathVariable Long id) {
        orderService.deleteOrderById(id);
    }
}

