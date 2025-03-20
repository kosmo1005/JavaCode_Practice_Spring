package com.kulushev.app.controller;

import com.kulushev.app.dto.auth.AuthRespDto;
import com.kulushev.app.dto.auth.RefreshReqDto;
import com.kulushev.app.dto.auth.SignInReqDto;
import com.kulushev.app.dto.UserReqDto;
import com.kulushev.app.dto.UserRespDto;
import com.kulushev.app.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<UserRespDto> signUp(@Valid @RequestBody UserReqDto userReqDto) throws Exception {
        return ResponseEntity.ok(authService.signUp(userReqDto));
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthRespDto> signIn(@Valid @RequestBody SignInReqDto signInReqDto) throws Exception {
        return ResponseEntity.ok(authService.signIn(signInReqDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthRespDto> refreshToken(@Valid @RequestBody RefreshReqDto reqDto) {
        return ResponseEntity.ok(authService.refreshToken(reqDto));
    }
}
