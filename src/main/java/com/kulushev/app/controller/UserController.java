package com.kulushev.app.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.kulushev.app.dto.UserRespDto;
import com.kulushev.app.views.UserFullNameProjection;
import com.kulushev.app.views.UserWithInfoAboutOrders;
import com.kulushev.app.views.Views;
import com.kulushev.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@RestController
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
    public UserRespDto getUserById(@PathVariable ("id") UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping("/full/{id}")
    @JsonView(Views.FullInfo.class)
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public UserRespDto getFullInfoByUserId(@PathVariable ("id") UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping("/fullName/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserFullNameProjection getFullNameById(@PathVariable ("id") UUID id) {
        return userService.getFullNameById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @GetMapping("/infoAboutOrders/{id}")
    public UserWithInfoAboutOrders getUserWithInfoAboutOrders(@PathVariable ("id") UUID id) {
        return userService.getUserWithInfoAboutOrders(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable ("id") UUID id) {
        userService.deleteUserById(id);
    }
}
