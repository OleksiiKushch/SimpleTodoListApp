package com.example.simpletodolistapp.facade.impl;

import com.example.simpletodolistapp.config.JwtService;
import com.example.simpletodolistapp.dto.LoginData;
import com.example.simpletodolistapp.dto.RegistrationData;
import com.example.simpletodolistapp.entity.Role;
import com.example.simpletodolistapp.entity.User;
import com.example.simpletodolistapp.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserFacadeImplTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encodedPassword";
    private static final String JWT_TOKEN = "jwtToken";

    @InjectMocks
    private UserFacadeImpl userFacade;

    @Mock
    private UserServiceImpl userServiceImpl;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @Captor
    private ArgumentCaptor<Map<String, Object>> claimsArgumentCaptor;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @Captor
    private ArgumentCaptor<UsernamePasswordAuthenticationToken> usernamePasswordAuthenticationTokenArgumentCaptor;

    @Mock
    private User user;
    @Mock
    private RegistrationData registrationData;
    @Mock
    private LoginData loginData;
    @Mock
    private User user1;
    @Mock
    private User user2;

    private List<User> users;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        users = new ArrayList<>(List.of(user1, user2));
        when(userServiceImpl.findAllUsers()).thenReturn(users);
        when(registrationData.getUsername()).thenReturn(USERNAME);
        when(registrationData.getPassword()).thenReturn(PASSWORD);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(loginData.getUsername()).thenReturn(USERNAME);
        when(loginData.getPassword()).thenReturn(PASSWORD);
        when(jwtService.generateToken(any(Map.class), any(User.class))).thenReturn(JWT_TOKEN);
        when(userServiceImpl.loadUserByUsername(USERNAME)).thenReturn(user);
    }

    @Test
    public void testGetAllUsers() {
        List<User> foundUsers = userFacade.getAllUsers();

        verify(userServiceImpl).findAllUsers();
        assertEquals(users, foundUsers);
    }

    @Test
    public void testRegistration() {
        String actualToken = userFacade.registration(registrationData);

        verify(userServiceImpl).createUser(userArgumentCaptor.capture());
        verify(jwtService).generateToken(claimsArgumentCaptor.capture(), userArgumentCaptor.capture());
        assertEquals(USERNAME, userArgumentCaptor.getValue().getUsername());
        assertEquals(ENCODED_PASSWORD, userArgumentCaptor.getValue().getPassword());
        assertEquals(Role.USER, userArgumentCaptor.getValue().getRole());
        assertEquals(JWT_TOKEN, actualToken);
    }

    @Test
    public void testLogin() {
        String actualToken = userFacade.login(loginData);

        verify(authenticationManager).authenticate(usernamePasswordAuthenticationTokenArgumentCaptor.capture());
        assertEquals(USERNAME, usernamePasswordAuthenticationTokenArgumentCaptor.getValue().getPrincipal());
        assertEquals(PASSWORD, usernamePasswordAuthenticationTokenArgumentCaptor.getValue().getCredentials());
        verify(jwtService).generateToken(claimsArgumentCaptor.capture(), userArgumentCaptor.capture());
        assertEquals(JWT_TOKEN, actualToken);
    }
}
