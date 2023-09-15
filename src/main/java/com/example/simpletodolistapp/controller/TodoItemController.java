package com.example.simpletodolistapp.controller;

import com.example.simpletodolistapp.entity.TodoItem;
import com.example.simpletodolistapp.service.impl.TodoItemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class TodoItemController {
    private final TodoItemServiceImpl todoItemServiceImpl;

    @PostMapping("/api/v1/createtodo")
    public TodoItem createTodoItem(@RequestBody TodoItem todoItem) {
        return todoItemServiceImpl.createTodoItem(todoItem);
    }
}
