package com.example.task1crud.service;

import com.example.task1crud.component.Constans;
import com.example.task1crud.entity.RoleType;
import com.example.task1crud.entity.Rollar;
import com.example.task1crud.entity.User;
import com.example.task1crud.payload.ApiResponse;
import com.example.task1crud.payload.RegisterDto;
import com.example.task1crud.payload.UserDto;
import com.example.task1crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse getAllUsers() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getRollar().getRoleType() == RoleType.ROLE_ADMIN) {
            List<User> all = userRepository.findAll();
            if (!all.isEmpty()) return new ApiResponse("success", true, all);
        } else {
            Optional<User> byId = userRepository.findById(principal.getId());
            if (byId.isPresent()) {
                return new ApiResponse("success", true, byId.get());
            }
        }
        return new ApiResponse("faild", false);
    }

    public ApiResponse getOneUser(Integer id) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getRollar().getRoleType() == RoleType.ROLE_ADMIN) {
            Optional<User> byId = userRepository.findById(id);
            if (!byId.isPresent()) return new ApiResponse("faild", false);
            return new ApiResponse("success", true, byId);
        }
        return new ApiResponse("faild", false);
    }

    public ApiResponse addUser(RegisterDto dto) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getRollar().getRoleType() != RoleType.ROLE_ADMIN) {
            return new ApiResponse("siz admin emassiz", false);
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            return new ApiResponse("email is already Exist", false);
        }
        if (userRepository.existsByPhoneNumber(dto.getPhone())) {
            return new ApiResponse("phone is already Exist", false);
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            return new ApiResponse("phone is already Exist", false);
        }
        if (!dto.getPassword().equals(dto.getPrePassword())) {
            return new ApiResponse("Password 1 xil emas", false);
        }

        User newUser = new User();
        newUser.setRollar(new Rollar(1, Constans.admin, RoleType.ROLE_ADMIN));
        newUser.setUsername(dto.getUsername());
        newUser.setEmail(dto.getEmail());
        newUser.setName(dto.getFullName());
        newUser.setPhoneNumber(dto.getPhone());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        User save = userRepository.save(newUser);
        return new ApiResponse("success", true, save);
    }

    public ApiResponse updateUser(UserDto dto, Integer id) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getRollar().getRoleType() != RoleType.ROLE_ADMIN) {
            return new ApiResponse("siz admin emassiz", false);
        }
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("fail. id not found", false);

        if (dto.getEmail() == null) {
            return new ApiResponse("email not found", false);
        }
        if (dto.getPhone() == null) {
            return new ApiResponse("phone not found", false);
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            return new ApiResponse("email is already Exist", false);
        }
        if (userRepository.existsByPhoneNumber(dto.getPhone())) {
            return new ApiResponse("phone is already Exist", false);
        }
        User user1 = byId.get();
        user1.setEmail(dto.getEmail());
        user1.setPhoneNumber(dto.getPhone());
        user1.setName(dto.getFullName());
        user1.setRollar(new Rollar(1, Constans.admin, RoleType.ROLE_ADMIN));
        User save = userRepository.save(user1);
        return new ApiResponse("success", true, save);
    }

    public ApiResponse updateMe(UserDto dto) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<User> byId = userRepository.findById(principal.getId());
        if (!byId.isPresent()) return new ApiResponse("fail. id not found", false);

        if (dto.getEmail() == null) {
            return new ApiResponse("email not found", false);
        }
        if (dto.getPhone() == null) {
            return new ApiResponse("phone not found", false);
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            return new ApiResponse("email is already Exist", false);
        }
        if (userRepository.existsByPhoneNumber(dto.getPhone())) {
            return new ApiResponse("phone is already Exist", false);
        }
        User user1 = byId.get();
        user1.setEmail(dto.getEmail());
        user1.setPhoneNumber(dto.getPhone());
        user1.setName(dto.getFullName());
        User save = userRepository.save(user1);
        return new ApiResponse("success", true, save);
    }

    public ApiResponse deleteByID(Integer id) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getRollar().getRoleType() == RoleType.ROLE_ADMIN) {

            Optional<User> byId = userRepository.findById(id);
            if (!byId.isPresent()) return new ApiResponse("fail. id not found", false);

            userRepository.delete(byId.get());
            return new ApiResponse("deleted", true, byId.get());
        }
        return new ApiResponse("fail. you are not admin", false);
    }

    public ApiResponse deleteMe() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userRepository.delete(principal);
        return new ApiResponse("deleted", true, principal);
    }

    public ApiResponse getDailyUsers(){
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principal.getRollar().getRoleType().equals(RoleType.ROLE_ADMIN))
            return new ApiResponse("sizga issiqlik qiladi",false);

        Timestamp timestamp=new Timestamp(System.currentTimeMillis()-1000*60*60*24);
        List<User> allByCreatedDateBefore = userRepository.findAllByCreatedDateBefore(timestamp);
        return new ApiResponse("success",true,allByCreatedDateBefore);
    }
}
