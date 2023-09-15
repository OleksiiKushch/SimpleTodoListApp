package com.example.simpletodolistapp.facade.impl;

import com.example.simpletodolistapp.config.JwtService;
import com.example.simpletodolistapp.dto.LoginData;
import com.example.simpletodolistapp.dto.RegistrationData;
import com.example.simpletodolistapp.entity.Role;
import com.example.simpletodolistapp.entity.User;
import com.example.simpletodolistapp.facade.UserFacade;
import com.example.simpletodolistapp.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Component
public class UserFacadeImpl implements UserFacade {
    private final UserServiceImpl userServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public List<User> getAllUsers() {
        return userServiceImpl.findAllUsers(); // TODO: convert to DTO
    }

    @Override
    public String registration(RegistrationData registrationData) {
        User user = new User();
        user.setUsername(registrationData.getUsername());
        user.setPassword(passwordEncoder.encode(registrationData.getPassword()));
        user.setRole(Role.USER);
        userServiceImpl.createUser(user);
        return jwtService.generateToken(new HashMap<>(), user);
    }

    @Override
    public String login(LoginData loginData) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword()));
        User user = userServiceImpl.loadUserByUsername(loginData.getUsername());
        return jwtService.generateToken(new HashMap<>(), user);
    }
}
