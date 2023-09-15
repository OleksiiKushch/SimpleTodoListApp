package com.example.simpletodolistapp.controller;

import com.example.simpletodolistapp.dto.LoginData;
import com.example.simpletodolistapp.dto.RegistrationData;
import com.example.simpletodolistapp.entity.User;
import com.example.simpletodolistapp.facade.impl.UserFacadeImpl;
import com.example.simpletodolistapp.validator.RegistrationDataValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserFacadeImpl userFacadeImpl;
    private final RegistrationDataValidator registrationDataValidator;

    @GetMapping("/api/v1/users")
    public List<User> getAllUsers() {
        return userFacadeImpl.getAllUsers();
    }

    @PostMapping("/api/v2/registration")
    public String registration2(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("confirmationPassword") String confirmationPassword,
                                   Errors errors) {
        var registrationData = RegistrationData.builder()
                .username(username)
                .password(password)
                .confirmationPassword(confirmationPassword)
                .build();

        registrationDataValidator.validate(registrationData, errors);
        if (errors.hasErrors()) {
            return errors.toString();
        } else {
            return userFacadeImpl.registration(registrationData);
        }
    }

    @PostMapping("/api/v1/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationData registrationData) {
        return ResponseEntity.ok(userFacadeImpl.registration(registrationData));
    }

    @PostMapping("/api/v1/login")
    public ResponseEntity<String> login(@RequestBody LoginData loginData) {
        return ResponseEntity.ok(userFacadeImpl.login(loginData));
    }
}
