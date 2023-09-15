package com.example.simpletodolistapp.service.impl;

import com.example.simpletodolistapp.entity.TodoList;
import com.example.simpletodolistapp.repository.TodoListRepository;
import com.example.simpletodolistapp.service.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoListServiceImpl implements TodoListService {
    private final TodoListRepository todoListRepository;

    @Override
    public TodoList createTodoList(TodoList todoList) {
        return todoListRepository.save(todoList);
    }

    @Override
    public List<TodoList> getAllTodoLists() {
        return todoListRepository.findAll();
    }
}
