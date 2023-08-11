package com.lisnenko.tasktracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String showLandingPage() {
        return "index";
    }

    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {
        return "login-form";
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "access-denied";
    }
}
