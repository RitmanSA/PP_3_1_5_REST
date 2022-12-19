package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@RestController
@CrossOrigin //for js
@RequestMapping("/api")
public class RestControllers {
    private final UserService userService;

    @Autowired
    public RestControllers (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getSingleUser(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        return new ResponseEntity<>(userService.getByEmail(user.getUsername()), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping(value = "/users", consumes = {"application/json;charset=UTF-8"})
    public ResponseEntity<?> addUser(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value="/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "users")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        userService.changeUser(user.getId(), user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
