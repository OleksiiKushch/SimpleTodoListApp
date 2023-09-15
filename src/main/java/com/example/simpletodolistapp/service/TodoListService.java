package com.example.simpletodolistapp.service;

import com.example.simpletodolistapp.entity.TodoList;

import java.util.List;

public interface TodoListService {
    TodoList createTodoList(TodoList todoList);
    List<TodoList> getAllTodoLists();
}
