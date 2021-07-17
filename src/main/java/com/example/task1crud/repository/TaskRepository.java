package com.example.task1crud.repository;

import com.example.task1crud.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findAllByUsers_Id(Integer users_id);
}
