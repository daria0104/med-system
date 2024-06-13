package com.example.service;

import com.example.entity.User;

import java.util.List;


public interface IUserService {
    boolean existsByUsernameOrEmail(String username, String email);
    User saveUser(User user);
    User getUserByEmail(String email);
    List<User> getAllUsers();
}
