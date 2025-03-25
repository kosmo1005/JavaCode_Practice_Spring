package com.kulushev.app.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.kulushev.app.dto.UserRespDto;
import com.kulushev.app.views.Views;
import com.kulushev.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/app/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public List<UserRespDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @JsonView(Views.ShortInfo.class)
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public UserRespDto getUserById(@PathVariable ("id") String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/full/{id}")
    @JsonView(Views.FullInfo.class)
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public UserRespDto getFullInfoByUserId(@PathVariable ("id") String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/infoAboutOrders/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @JsonView(Views.FullInfo.class)
    public String getUserWithInfoAboutOrders(@PathVariable ("id") String id, Model model) {
        try {
            UserRespDto userRespDto = userService.getUserWithInfoAboutOrders(id);
            model.addAttribute("user", userRespDto);
            return "getUserWithInfoAboutOrders";
        } catch (Exception e) {
            model.addAttribute("statusCode", 500);
            model.addAttribute("errorMessage", "Ошибка вывода списка заказов: " + e.getMessage());
            return "errorPage";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable ("id") String id) {
        userService.deleteUserById(id);
    }
}
