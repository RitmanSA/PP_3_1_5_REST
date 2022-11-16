package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class TestController {

    @GetMapping("/user")
    public String printUser(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "user";
    }

}
