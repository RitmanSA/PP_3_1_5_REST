package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class TestController {

    private final UserService userService;
    @Autowired
    public TestController (UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/user")
    public String printUser(Model model, Principal principal) {
        User user = userService.getByEmail(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

}
