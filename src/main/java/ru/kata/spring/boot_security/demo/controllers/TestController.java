package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class TestController {

    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public TestController (UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("/user")
    public String userPage(Model model, Principal principal) {
        User user = userService.getByEmail(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/admin/add")
    public String newEmptyUser(Model model) {
        model.addAttribute("rolesList", roleService.getAllRoles());
        model.addAttribute("user", new User());
        model.addAttribute("rolesArray", new String[2]);
        return "form";
    }

    @PostMapping("/admin/update")
    public String newUser(@ModelAttribute("user") User user, @RequestParam(name = "rolesArray") String[] rolesArray) {
        userService.addUser(user);
        return "redirect:/admin";
    }


}
