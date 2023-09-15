package com.example.simpletodolistapp.service.impl;

import com.example.simpletodolistapp.entity.User;
import com.example.simpletodolistapp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String TEST_USER_NAME = "testUser";
    private static final String NOT_EXISTENT_USER_NAME = "nonExistentUser";

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;
    @Mock
    private User user1;
    @Mock
    private User user2;

    private ArrayList<User> users;

    @Before
    public void setUp() {
        when(userRepository.save(user)).thenReturn(user);
        users = new ArrayList<>(List.of(user1, user2));
        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.findByUsername(TEST_USER_NAME)).thenReturn(Optional.of(user));
    }

    @Test
    public void testCreateUser() {
        User savedUser = userServiceImpl.createUser(user);

        verify(userRepository).save(user);
        assertEquals(user, savedUser);
    }

    @Test
    public void testFindAllUsers() {
        List<User> foundUsers = userServiceImpl.findAllUsers();

        verify(userRepository).findAll();
        assertEquals(users, foundUsers);
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        User loadedUser = userServiceImpl.loadUserByUsername(TEST_USER_NAME);

        verify(userRepository).findByUsername(TEST_USER_NAME);
        assertEquals(user, loadedUser);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername(NOT_EXISTENT_USER_NAME)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername(NOT_EXISTENT_USER_NAME));
        verify(userRepository).findByUsername(NOT_EXISTENT_USER_NAME);
    }
}
