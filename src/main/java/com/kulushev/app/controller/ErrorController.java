package com.kulushev.app.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/app")
@RequiredArgsConstructor
public class ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @GetMapping("/error")
    public String authErrorPage(Model model, @RequestParam(required = false) String error) {
        logger.error("Error page accessed with error: {}", error);
        model.addAttribute("errorMessage", error != null ? error : "Произошла11 ошибка.");
        return "errorPage";
    }
}
