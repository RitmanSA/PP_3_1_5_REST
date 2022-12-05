package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.stream.Collectors;

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
    public String adminPage(Model model, Principal principal) {
        User admin = userService.getByEmail(principal.getName());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("admin", admin);
        model.addAttribute("newUser", new User());
        model.addAttribute("rolesArray", roleService.getAllRoles().stream().map(Role::getRoleName).toArray());
        model.addAttribute("updateUser", new User());
        return "admin";
    }

    @PostMapping("/admin")
    public String newUser(@ModelAttribute("NewUser") User user, @RequestParam("rolesArray") String[] rolesArray) {
        user.setRoleList(Arrays.stream(rolesArray).map(roleService::getByRole).collect(Collectors.toList()));
        userService.addUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/update/{id}")
    public String update(@PathVariable("id") Long id
            , @ModelAttribute("updateUser") User user
            , @RequestParam("rolesArray") String[] rolesArray) {
        user.setRoleList(Arrays.stream(rolesArray).map(roleService::getByRole).collect(Collectors.toList()));
        userService.changeUser(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }
}
