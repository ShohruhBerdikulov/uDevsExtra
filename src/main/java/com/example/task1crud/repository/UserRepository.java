package com.example.task1crud.repository;

import com.example.task1crud.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNum);
    boolean existsByUsername(String username);

    List<User> findAllByCreatedDateBefore(Timestamp createdDate);
}
