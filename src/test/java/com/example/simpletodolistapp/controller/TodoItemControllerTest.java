package com.example.simpletodolistapp.controller;

import com.example.simpletodolistapp.entity.TodoItem;
import com.example.simpletodolistapp.service.impl.TodoItemServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TodoItemControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private TodoItemController todoItemController;

    @Mock
    private TodoItemServiceImpl todoItemServiceImpl;

    @Mock
    private TodoItem todoItem;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(todoItemController).build();
    }

    @Test
    public void testCreateTodoItem() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String todoItemJson = objectMapper.writeValueAsString(todoItem);

        mockMvc.perform(post("/api/v1/createtodo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(todoItemJson))
                .andExpect(status().isOk());
        verify(todoItemServiceImpl).createTodoItem(any(TodoItem.class));
    }
}
