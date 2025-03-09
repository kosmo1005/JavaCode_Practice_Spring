package com.kulushev.app.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.kulushev.app.dto.UserReqDto;
import com.kulushev.app.dto.UserRespDto;
import com.kulushev.app.views.Views;
import com.kulushev.app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<UserRespDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @JsonView(Views.ShortInfo.class)
    public UserRespDto getUserById(@PathVariable ("id") UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping("/full/{id}")
    @JsonView(Views.FullInfo.class)
    public UserRespDto getFullInfoByUserId(@PathVariable ("id") UUID id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @JsonView(Views.FullInfo.class)
    public UserRespDto createUser(@Valid @RequestBody UserReqDto reqDto) {
        return userService.createUser(reqDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable ("id") UUID id) {
        userService.deleteUserById(id);
    }
}
