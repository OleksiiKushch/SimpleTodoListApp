package com.example.simpletodolistapp.service;

import com.example.simpletodolistapp.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> findAllUsers();
}
