package com.example.simpletodolistapp.controller;

import com.example.simpletodolistapp.dto.LoginData;
import com.example.simpletodolistapp.dto.RegistrationData;
import com.example.simpletodolistapp.entity.User;
import com.example.simpletodolistapp.facade.impl.UserFacadeImpl;
import com.example.simpletodolistapp.validator.RegistrationDataValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String TEST_USER_NAME1 = "user1";
    private static final String TEST_USER_NAME2 = "user2";
    private static final String JWT_TOKEN = "jwtToken";


    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserFacadeImpl userFacadeImpl;
    @Mock
    private RegistrationDataValidator registrationDataValidator;

    @Captor
    private ArgumentCaptor<RegistrationData> registrationDataArgumentCaptor;
    @Captor
    private ArgumentCaptor<LoginData> loginDataArgumentCaptor;

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
    public void setUp() {
        when(user1.getUsername()).thenReturn(TEST_USER_NAME1);
        when(user2.getUsername()).thenReturn(TEST_USER_NAME2);
        users = new ArrayList<>(List.of(user1, user2));
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        when(userFacadeImpl.getAllUsers()).thenReturn(users);
        when(userFacadeImpl.registration(any(RegistrationData.class))).thenReturn(JWT_TOKEN);
        when(userFacadeImpl.login(any(LoginData.class))).thenReturn(JWT_TOKEN);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value(TEST_USER_NAME1))
                .andExpect(jsonPath("$[1].username").value(TEST_USER_NAME2));
        verify(userFacadeImpl).getAllUsers();
    }

    @Test
    public void testRegistration() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String registrationDataJson = objectMapper.writeValueAsString(registrationData);

        mockMvc.perform(post("/api/v1/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registrationDataJson))
                .andExpect(status().isOk())
                .andExpect(content().string(JWT_TOKEN));
        verify(userFacadeImpl).registration(any(RegistrationData.class));
        verifyNoMoreInteractions(userFacadeImpl);
    }

    @Test
    public void testLogin() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String loginDataJson = objectMapper.writeValueAsString(loginData);

        mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginDataJson))
                .andExpect(status().isOk())
                .andExpect(content().string(JWT_TOKEN));
        verify(userFacadeImpl).login(any(LoginData.class));
        verifyNoMoreInteractions(userFacadeImpl);
    }
}

