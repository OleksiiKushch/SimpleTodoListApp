package com.example.simpletodolistapp.controller;

import com.example.simpletodolistapp.entity.TodoList;
import com.example.simpletodolistapp.service.impl.TodoListServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TodoListController {
    private final TodoListServiceImpl todoListServiceImpl;

    @GetMapping("/api/v1/todolists")
    public List<TodoList> getTodoLists() {
        return todoListServiceImpl.getAllTodoLists();
    }

    @PostMapping("/api/v1/createtodolist")
    public TodoList createTodoList(@RequestParam("listName") String listName) {
        TodoList newTodoList = new TodoList();
        newTodoList.setName(listName);
        return todoListServiceImpl.createTodoList(newTodoList);
    }
}
