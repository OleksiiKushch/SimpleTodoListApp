package com.example.simpletodolistapp.repository;

import com.example.simpletodolistapp.entity.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoListRepository extends JpaRepository<TodoList, Long>  {
}
