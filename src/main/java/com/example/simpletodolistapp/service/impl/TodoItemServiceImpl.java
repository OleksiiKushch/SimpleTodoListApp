package com.example.simpletodolistapp.service.impl;

import com.example.simpletodolistapp.entity.TodoItem;
import com.example.simpletodolistapp.repository.TodoItemRepository;
import com.example.simpletodolistapp.service.TodoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodoItemServiceImpl implements TodoItemService {

    private final TodoItemRepository todoItemRepository;

    @Override
    public TodoItem createTodoItem(TodoItem todoItem) {
        return todoItemRepository.save(todoItem);
    }
}
