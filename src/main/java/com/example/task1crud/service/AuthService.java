package com.example.task1crud.service;

import com.example.task1crud.component.Constans;
import com.example.task1crud.entity.RoleType;
import com.example.task1crud.entity.Rollar;
import com.example.task1crud.entity.User;
import com.example.task1crud.payload.ApiResponse;
import com.example.task1crud.payload.RegisterDto;
import com.example.task1crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService   {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse registerUser(RegisterDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            return new ApiResponse("email is already Exist", false);
        }
        if (userRepository.existsByPhoneNumber(dto.getPhone())) {
            return new ApiResponse("phone is already Exist", false);
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            return new ApiResponse("phone is already Exist", false);
        }
        if (!dto.getPassword().equals(dto.getPrePassword()))
            return new ApiResponse("Password 1 xil emas", false);
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhone());
        user.setRollar(new Rollar(2, Constans.user, RoleType.ROLE_USER));

        User save = userRepository.save(user);

        return new ApiResponse("User saved", true, save);
    }

    public UserDetails loadUserByUsername(String user) {
        return userRepository.findByUsername(user).orElseThrow(() -> new UsernameNotFoundException(user));
    }
}

