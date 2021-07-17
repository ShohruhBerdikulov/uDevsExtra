package com.example.task1crud.controller;

import com.example.task1crud.entity.User;
import com.example.task1crud.payload.ApiResponse;
import com.example.task1crud.payload.LoginDto;
import com.example.task1crud.payload.RegisterDto;
import com.example.task1crud.repository.UserRepository;
import com.example.task1crud.security.JWTProvider;
import com.example.task1crud.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService service;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTProvider jwtProvider;

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@NotNull @RequestBody RegisterDto dto) {
        ApiResponse apiResponse = service.registerUser(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> loginUser(@NotNull @RequestBody LoginDto dto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            User principal = (User) authenticate.getPrincipal();
            String generateToken = jwtProvider.generateToken(principal.getUsername(), principal.getRollar());
            return ResponseEntity.ok(generateToken);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("xato");
        }

    }
}
