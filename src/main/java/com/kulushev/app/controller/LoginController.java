package com.kulushev.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/app/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/app/logout")
    public String logoutPage() {
        return "logout";
    }
}
