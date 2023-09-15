package com.example.simpletodolistapp.mapper.impl;

import com.example.simpletodolistapp.dto.RegistrationData;
import com.example.simpletodolistapp.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationDataToUserMapperTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @InjectMocks
    private RegistrationDataToUserMapper mapper;

    @Captor
    private ArgumentCaptor<String> usernameArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> passwordArgumentCaptor;

    @Mock
    private RegistrationData registrationData;
    @Mock
    private User user;

    @Before
    public void setUp() {
        when(registrationData.getUsername()).thenReturn(USERNAME);
        when(registrationData.getPassword()).thenReturn(PASSWORD);
    }

    @Test
    public void testMap() {
        mapper.map(registrationData, user);

        verify(user).setUsername(usernameArgumentCaptor.capture());
        assertEquals(USERNAME, usernameArgumentCaptor.getValue());
        verify(user).setPassword(passwordArgumentCaptor.capture());
        assertEquals(PASSWORD, passwordArgumentCaptor.getValue());
    }
}
