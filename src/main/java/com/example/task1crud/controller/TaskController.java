package com.example.task1crud.controller;

import com.example.task1crud.payload.TaskDto;
import com.example.task1crud.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    TaskService service;

    @GetMapping("/withUserId/{id}") //admin
    public HttpEntity<?> getAll(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.getAllTasksByUSerId(id));
    }

    @GetMapping("/{id}") //all
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.getOneTask(id));
    }

    @GetMapping//all
    public HttpEntity<?> getMy() {
        return ResponseEntity.ok().body(service.getMyTasks());
    }


    @DeleteMapping("/{id}") //all
    public HttpEntity<?> deleteById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.deleteByID(id));
    }

    @PutMapping("/{id}")  //all
    public HttpEntity<?> update(@RequestBody TaskDto dto, @PathVariable Integer id) {
        return ResponseEntity.ok().body(service.updateTask(dto, id));
    }

    @PostMapping//all
    public HttpEntity<?> add(@RequestBody TaskDto dto) {
        return ResponseEntity.ok().body(service.addTask(dto));
    }


}
