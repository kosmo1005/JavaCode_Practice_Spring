package com.kulushev.app.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.kulushev.app.JsonMapper;
import com.kulushev.app.dto.UserReqDto;
import com.kulushev.app.dto.UserRespDto;
import com.kulushev.app.enums.Views;
import com.kulushev.app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RestController
@RequestMapping("/app/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> getAllUsers() {
        var str = JsonMapper.listUserRespDto_ToJson(userService.getAllUsers(), Views.SHORT_INFO);
        return ResponseEntity.ok(str);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getUserById(@PathVariable("id") UUID id) {
        var str = JsonMapper.userRespDto_ToJson(userService.getUserById(id), Views.SHORT_INFO);
        return ResponseEntity.ok(str);
    }

    @GetMapping("/full/{id}")
    public ResponseEntity<String> getFullInfoByUserId(@PathVariable("id") UUID id) {
        var str = JsonMapper.userRespDto_ToJson(userService.getUserById(id), Views.FULL_INFO);
        return ResponseEntity.ok(str);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody String json) {
        var reqDto = JsonMapper.json_ToUserReqDto(json);
        var str = JsonMapper.userRespDto_ToJson(userService.createUser(reqDto), Views.FULL_INFO);
        return ResponseEntity.ok(str);

    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUserById(id);
    }
}
