package com.example.simpletodolistapp.service.impl;

import com.example.simpletodolistapp.entity.TodoItem;
import com.example.simpletodolistapp.repository.TodoItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TodoItemServiceImplTest {
    @InjectMocks
    private TodoItemServiceImpl todoItemServiceImpl;

    @Mock
    private TodoItemRepository todoItemRepository;

    @Mock
    private TodoItem todoItem;

    @Before
    public void setUp() {
        when(todoItemRepository.save(todoItem)).thenReturn(todoItem);
    }

    @Test
    public void testCreateTodoItem() {
        TodoItem savedTodoItem = todoItemServiceImpl.createTodoItem(todoItem);

        verify(todoItemRepository).save(todoItem);
        assertEquals(todoItem, savedTodoItem);
    }
}
