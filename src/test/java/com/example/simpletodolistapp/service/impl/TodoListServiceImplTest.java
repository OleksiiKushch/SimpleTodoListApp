package com.example.simpletodolistapp.service.impl;

import com.example.simpletodolistapp.entity.TodoList;
import com.example.simpletodolistapp.repository.TodoListRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TodoListServiceImplTest {
    @InjectMocks
    private TodoListServiceImpl todoListService;

    @Mock
    private TodoListRepository todoListRepository;

    @Mock
    private TodoList todoList;
    @Mock
    private TodoList todoList1;
    @Mock
    private TodoList todoList2;

    List<TodoList> todoLists;

    @Before
    public void setUp() {
        when(todoListRepository.save(todoList)).thenReturn(todoList);
        todoLists = new ArrayList<>(List.of(todoList1, todoList2));
        when(todoListRepository.findAll()).thenReturn(todoLists);
    }

    @Test
    public void testCreateTodoList() {
        TodoList savedTodoList = todoListService.createTodoList(todoList);

        verify(todoListRepository).save(todoList);
        assertEquals(todoList, savedTodoList);
    }

    @Test
    public void testGetAllTodoLists() {
        List<TodoList> foundTodoLists = todoListService.getAllTodoLists();

        verify(todoListRepository).findAll();
        assertEquals(todoLists, foundTodoLists);
    }
}
