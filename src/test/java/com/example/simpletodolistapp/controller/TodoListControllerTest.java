package com.example.simpletodolistapp.controller;

import com.example.simpletodolistapp.entity.TodoList;
import com.example.simpletodolistapp.service.impl.TodoListServiceImpl;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
public class TodoListControllerTest {

    private static final String LIST_NAME_PARAM = "listName";
    private static final String LIST_NAME = "New List";
    private static final String TODO_LIST_NAME1 = "List 1";
    private static final String TODO_LIST_NAME2 = "List 2";

    private MockMvc mockMvc;

    @InjectMocks
    private TodoListController todoListController;

    @Mock
    private TodoListServiceImpl todoListServiceImpl;

    @Captor
    private ArgumentCaptor<TodoList> todoListArgumentCaptor;

    @Mock
    private TodoList todoList;
    @Mock
    private TodoList todoList1;
    @Mock
    private TodoList todoList2;

    List<TodoList> todoLists;

    @Before
    public void setUp() {
        when(todoList1.getName()).thenReturn(TODO_LIST_NAME1);
        when(todoList2.getName()).thenReturn(TODO_LIST_NAME2);
        todoLists = new ArrayList<>(List.of(todoList1, todoList2));
        mockMvc = MockMvcBuilders.standaloneSetup(todoListController).build();
        when(todoList.getName()).thenReturn(LIST_NAME);
        when(todoListServiceImpl.getAllTodoLists()).thenReturn(todoLists);
        when(todoListServiceImpl.createTodoList(any(TodoList.class))).thenReturn(todoList);
    }

    @Test
    public void testGetTodoLists() throws Exception {
        mockMvc.perform(get("/api/v1/todolists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("List 1"))
                .andExpect(jsonPath("$[1].name").value("List 2"));
        verify(todoListServiceImpl).getAllTodoLists();
    }

    @Test
    public void testCreateTodoList() throws Exception {
        mockMvc.perform(post("/api/v1/createtodolist")
                        .param(LIST_NAME_PARAM, LIST_NAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(LIST_NAME));
        verify(todoListServiceImpl).createTodoList(todoListArgumentCaptor.capture());
        assertEquals(LIST_NAME, todoListArgumentCaptor.getValue().getName());
    }
}
