package com.example.simpletodolistapp.mapper.impl;

import com.example.simpletodolistapp.dto.RegistrationData;
import com.example.simpletodolistapp.entity.User;
import com.example.simpletodolistapp.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class RegistrationDataToUserMapper implements Mapper<RegistrationData, User> {

    @Override
    public void map(RegistrationData registrationData, User user) {
        user.setUsername(registrationData.getUsername());
        user.setPassword(registrationData.getPassword());
    }
}
