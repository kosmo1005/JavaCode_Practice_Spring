package com.kulushev.app.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/app/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/oauthSuccess")
    public String oauthSuccess(Model model, @AuthenticationPrincipal OAuth2User user) {
        try {
        model.addAttribute("id", user.getAttribute("id"));
        model.addAttribute("login", user.getAttribute("login"));
        model.addAttribute("name", user.getAttribute("name"));
        model.addAttribute("email", user.getAttribute("email"));
        return "oauthSuccess";
        } catch (Exception e) {
            logger.error("Ошибка в oauthSuccess", e);
            return "errorPage";
        }
    }

    @GetMapping("/error")
    public String authErrorPage(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("errorMessage", error != null ? error : "Произошла ошибка во время авторизации.");
        return "errorPage";
    }

}
