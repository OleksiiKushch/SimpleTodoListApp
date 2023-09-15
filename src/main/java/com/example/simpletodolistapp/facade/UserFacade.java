package com.example.simpletodolistapp.facade;

import com.example.simpletodolistapp.dto.LoginData;
import com.example.simpletodolistapp.dto.RegistrationData;
import com.example.simpletodolistapp.entity.User;

import java.util.List;

public interface UserFacade {
    List<User> getAllUsers();
    String registration(RegistrationData registrationData);
    String login(LoginData loginData);
}
