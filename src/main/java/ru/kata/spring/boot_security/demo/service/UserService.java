package ru.kata.spring.boot_security.demo.service;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService {
    void addUser(User user);
    void deleteUser(Long id);
    void changeUser(Long id, User newUser);
    User getById(Long id);
    User getByEmail(String email);
    List<User> getAllUsers();
}
