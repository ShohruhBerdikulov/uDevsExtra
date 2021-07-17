package com.example.task1crud.controller;

import com.example.task1crud.payload.RegisterDto;
import com.example.task1crud.payload.UserDto;
import com.example.task1crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService service;

    @GetMapping
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok().body(service.getAllUsers());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.getOneUser(id));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.deleteByID(id));
    }

    @DeleteMapping()
    public HttpEntity<?> deleteOne() {
        return ResponseEntity.ok().body(service.deleteMe());
    }

    @PutMapping("/{id}")
    public HttpEntity<?> update(@RequestBody UserDto user, @PathVariable Integer id) {
        return ResponseEntity.ok().body(service.updateUser(user, id));
    }

    @PutMapping
    public HttpEntity<?> updateMe(@RequestBody UserDto user) {
        return ResponseEntity.ok().body(service.updateMe(user));
    }

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody RegisterDto user) {
        return ResponseEntity.ok().body(service.addUser(user));
    }

    @GetMapping("/daily")
    public HttpEntity<?> getDaily() {
        return ResponseEntity.ok().body(service.getDailyUsers());
    }


}
