package com.example.simpletodolistapp.repository;

import com.example.simpletodolistapp.entity.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
}
